package com.teej107.mediaplayer.platform.os;

import com.teej107.mediaplayer.Application;
import com.teej107.mediaplayer.platform.Platform;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by teej107 on 4/15/17.
 */
public class MacPlatform implements OSPlatform
{
	@Override
	public Path getAppDataDirectory()
	{
		return Paths.get(Platform.getHome(), "Library", "Application Support", Application.instance().getName());
	}
}
