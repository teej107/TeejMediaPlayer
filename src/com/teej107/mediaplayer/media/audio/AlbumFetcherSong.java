package com.teej107.mediaplayer.media.audio;

import java.awt.*;
import java.net.URI;

/**
 * Created by teej107 on 5/10/17.
 */
public class AlbumFetcherSong implements ISong
{
	private String artist, album;

	public AlbumFetcherSong(String artist, String album)
	{
		this.artist = artist;
		this.album = album;
	}

	@Override
	public String getTitle()
	{
		return null;
	}

	@Override
	public String getArtist()
	{
		return artist;
	}

	@Override
	public String getGenre()
	{
		return null;
	}

	@Override
	public String getAlbum()
	{
		return album;
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
	public Image getArtwork()
	{
		return null;
	}

	@Override
	public URI getURI()
	{
		return null;
	}
}
