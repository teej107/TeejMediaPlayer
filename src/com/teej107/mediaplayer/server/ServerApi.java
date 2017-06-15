package com.teej107.mediaplayer.server;

import com.teej107.mediaplayer.Application;
import com.teej107.mediaplayer.media.audio.DatabaseSong;
import com.teej107.mediaplayer.util.Util;
import org.json.simple.JSONObject;

import java.io.File;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * Created by teej107 on 5/3/2017.
 */
public class ServerApi
{
	private static final String ALBUM_EXT = ".jpg";

	private Application application;

	public ServerApi(Application application)
	{
		this.application = application;
	}

	private URI getSongURI(String urlPath)
	{
		Path musicDir = application.getApplicationPreferences().getMusicRootDirectory();
		if (musicDir == null)
			return null;

		return musicDir.resolve(urlPath).toUri();
	}

	public List<String> getArtists()
	{
		return application.getDatabaseManager().getArtists();
	}

	public String getLibraryJSON()
	{
		Map map = new LinkedHashMap();
		for (DatabaseSong song : application.getDatabaseManager().getLibrary())
		{
			JSONObject songJson = Util.toJSONObject(song);
			map.put(songJson.get("path"), songJson);
		}
		return JSONObject.toJSONString(map);
	}

	public String getSongFile(String urlPath)
	{
		URI uri = getSongURI(urlPath);
		if(uri == null)
			return null;
		DatabaseSong song = application.getDatabaseManager().getSongByURI(uri);
		if (song != null)
			return new File(uri).getAbsolutePath();

		return null;
	}

	public String getAlbumArt(String artist, String album)
	{
		album = stripAlbumExtension(album);
		List<DatabaseSong> list = application.getDatabaseManager().getAlbumByArtist(artist, album);
		if(list.size() == 0)
			return null;
		Path path = application.getAlbumManager().getAlbumCoverPath(list.get(0));
		if(!Files.exists(path))
		{
			application.getAlbumManager().getAndSaveAlbumCover(list.get(0));
		}
		return path.toString();
	}

	public String getAlbumArtFromHex(String hexArtist, String hexAlbum)
	{
		hexAlbum = stripAlbumExtension(hexAlbum);
		return getAlbumArt(Util.fromHexString(hexArtist), Util.fromHexString(hexAlbum));
	}

	private static String stripAlbumExtension(String str)
	{
		return str.toLowerCase().endsWith(ALBUM_EXT) ? str.substring(0, str.length() - ALBUM_EXT.length()) : str;
	}

	public String getSongJSON(String urlPath)
	{
		URI uri = getSongURI(urlPath);
		if(uri != null)
		{
			DatabaseSong song = application.getDatabaseManager().getSongByURI(uri);
			if(song == null)
				return "{}";

			return Util.toJSONObject(song).toString();
		}
		return "{}";
	}
}
