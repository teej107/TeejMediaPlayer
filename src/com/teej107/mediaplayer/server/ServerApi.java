package com.teej107.mediaplayer.server;

import com.teej107.mediaplayer.Application;
import com.teej107.mediaplayer.media.audio.DatabaseSong;
import com.teej107.mediaplayer.util.Util;

import java.io.File;
import java.net.URI;
import java.nio.file.Path;

/**
 * Created by teej107 on 5/3/2017.
 */
public class ServerApi
{
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

	public String getSongJSON(String urlPath)
	{
		URI uri = getSongURI(urlPath);
		if(uri != null)
		{
			DatabaseSong song = application.getDatabaseManager().getSongByURI(uri);
			if(song == null)
				return "{}";

			return Util.toJSON(song, urlPath);
		}
		return "{}";
	}
}
