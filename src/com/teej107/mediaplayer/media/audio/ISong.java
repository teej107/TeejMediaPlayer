package com.teej107.mediaplayer.media.audio;

import com.teej107.mediaplayer.media.IPlaybackControl;

/**
 * Created by teej107 on 4/15/2017.
 */
public interface ISong extends IPlaybackControl
{
	String getName();
	String getArtist();
	String getGenre();
	String getAlbum();
	int getYear();
	long getDuration();
}
