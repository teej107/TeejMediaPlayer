package com.teej107.mediaplayer.media.audio;

import com.teej107.mediaplayer.io.db.Row;

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
		return row.getObject("year", int.class);
	}

	@Override
	public long getDuration()
	{
		return row.getObject("duration", long.class);
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
}
