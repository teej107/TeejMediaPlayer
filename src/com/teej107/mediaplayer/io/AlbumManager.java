package com.teej107.mediaplayer.io;

import com.teej107.mediaplayer.media.audio.ISong;
import com.teej107.mediaplayer.util.*;
import radams.gracenote.webapi.*;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

/**
 * Created by teej107 on 4/28/2017.
 */
public class AlbumManager
{
	private static final String IMAGE_FORMAT = "jpg";

	private GracenoteWebAPI gracenoteWebAPI;
	private ApplicationPreferences applicationPreferences;

	public AlbumManager(ApplicationPreferences applicationPreferences)
	{
		this.applicationPreferences = applicationPreferences;
		String userID = (String) applicationPreferences.getApiState().getGracenote().get(ApiState.GRACENOTE_API_USERID);
		try
		{
			if (userID == null)
			{
				this.gracenoteWebAPI = new GracenoteWebAPI(Secret.instance().getGracenoteClientID(),
						Secret.instance().getGracenoteClientTag());
				userID = gracenoteWebAPI.register();
				applicationPreferences.getApiState().getGracenote().put(ApiState.GRACENOTE_API_USERID, userID);
			}
			else
			{
				this.gracenoteWebAPI = new GracenoteWebAPI(Secret.instance().getGracenoteClientID(),
						Secret.instance().getGracenoteClientTag(), userID);
			}
		}
		catch (GracenoteException e)
		{
			e.printStackTrace();
		}
	}

	public Image fetchAlbumCover(ISong song)
	{
		GracenoteMetadata metadata = gracenoteWebAPI.searchAlbum(song.getArtist(), song.getAlbum());
		List<Map<String, Object>> data = metadata.getAlbums();
		if(data.size() > 0)
		{
			String artUrl = (String) data.get(0).get("album_coverart");
			try
			{
				return ImageIO.read(new URL(artUrl));
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		return null;
	}

	public Response saveAlbumCover(ISong song, Image image)
	{
		Path path = getAlbumCoverPath(song);
		try
		{
			Files.createDirectories(path.getParent());
			ImageIO.write(ImageUtil.toBufferedImage(image), IMAGE_FORMAT, path.toFile());
			return Response.createOkResponse();
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return Response.createErrorResponse(e.getMessage());
		}
	}

	public Path getAlbumCoverPath(ISong song)
	{
		String artist = Util.toHexString(song.getArtist());
		String album = Util.toHexString(song.getAlbum());
		return applicationPreferences.getAlbumArtRootDirectory().resolve(artist).resolve(album + "." + IMAGE_FORMAT);
	}

	public Image getAlbumCover(ISong song)
	{
		Path path = getAlbumCoverPath(song);
		if(!Files.exists(path))
			return null;

		try
		{
			return ImageIO.read(path.toUri().toURL());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		return null;
	}

	public Image getAndSaveAlbumCover(ISong song)
	{
		Image image = getAlbumCover(song);
		if(image == null)
		{
			image = fetchAlbumCover(song);
			if(image != null)
			{
				saveAlbumCover(song, image);
			}
		}
		return image;
	}
}
