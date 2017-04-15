package com.teej107.mediaplayer.media.audio;

import javax.print.attribute.standard.Media;
import java.net.URI;
import java.nio.file.Path;

/**
 * Created by teej107 on 4/15/17.
 */
public class DatabaseSong implements ISong
{
	private DatabaseSong()
	{

	}

	@Override
	public String getName()
	{
		return null;
	}

	@Override
	public String getArtist()
	{
		return null;
	}

	@Override
	public String getGenre()
	{
		return null;
	}

	@Override
	public String getAlbum()
	{
		return null;
	}

	@Override
	public int getYear()
	{
		return 0;
	}

	@Override
	public long getDuration()
	{
		return 0;
	}

	@Override
	public URI getURI()
	{
		return null;
	}
}
