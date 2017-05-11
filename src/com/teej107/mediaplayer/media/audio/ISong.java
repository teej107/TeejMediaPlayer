package com.teej107.mediaplayer.media.audio;

import java.awt.*;
import java.net.URI;

/**
 * Created by teej107 on 4/15/2017.
 */
public interface ISong
{
	String getTitle();
	String getArtist();
	String getGenre();
	String getAlbum();
	int getTrackNumber();
	int getYear();
	long getDuration();
	Image getArtwork();
	URI getURI();
}
