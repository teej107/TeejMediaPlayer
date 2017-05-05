package com.teej107.mediaplayer.media.audio;

import com.teej107.mediaplayer.io.db.Row;
import com.teej107.mediaplayer.util.Util;
import org.jaudiotagger.audio.AudioFile;
import org.jaudiotagger.audio.AudioFileIO;
import org.jaudiotagger.tag.images.Artwork;

import java.awt.*;
import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;

/**
 * Created by teej107 on 4/15/17.
 */
public class DatabaseSong implements ISong
{
	private Row row;

	public DatabaseSong(Row row)
	{
		this.row = row;
	}

	@Override
	public String getTitle()
	{
		return row.getObject("title", String.class);
	}

	@Override
	public String getArtist()
	{
		return row.getObject("artist", String.class);
	}

	@Override
	public String getGenre()
	{
		return row.getObject("genre", String.class);
	}

	@Override
	public String getAlbum()
	{
		return row.getObject("album", String.class);
	}

	@Override
	public int getYear()
	{
		return row.getObject("year", Integer.class);
	}

	@Override
	public long getDuration()
	{
		return row.getObject("duration_sec", Integer.class);
	}

	@Override
	public Image getArtwork()
	{
		try
		{
			AudioFile f = AudioFileIO.read(new File(getURI()));
			Artwork artwork = f.getTag().getFirstArtwork();
			return artwork == null ? null : (Image) artwork.getImage();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public URI getURI()
	{
		try
		{
			return new URI(row.getObject("uri", String.class));
		}
		catch (URISyntaxException e)
		{
			e.printStackTrace();
			return null;
		}
	}

	public Row getRow()
	{
		return row;
	}

	@Override
	public String toString()
	{
		return Util.toJSON(this);
	}
}
