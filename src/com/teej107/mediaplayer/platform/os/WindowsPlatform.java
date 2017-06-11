package com.teej107.mediaplayer.platform.os;

import com.teej107.mediaplayer.Application;
import com.teej107.mediaplayer.platform.Platform;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by teej107 on 4/15/17.
 */
public class WindowsPlatform implements OSPlatform
{
	@Override
	public Path getAppDataDirectory()
	{
		return Paths.get(System.getenv("appdata"), Application.instance().getName());
	}

	@Override
	public String getTerminate()
	{
		return "Exit";
	}

	@Override
	public String getLocalAddress()
	{
		return Platform.getDefault().getLocalAddress();
	}
}
