package com.teej107.mediaplayer.platform.os;

import com.teej107.mediaplayer.platform.Platform;

import java.nio.file.Path;

/**
 * Created by teej107 on 4/15/17.
 */
public class MacPlatform implements OSPlatform
{
	@Override
	public Path getAppDataDirectory()
	{
		return null;
	}
}
