package com.teej107.mediaplayer.platform;

import com.teej107.mediaplayer.Application;
import com.teej107.mediaplayer.platform.os.*;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by teej107 on 4/15/17.
 */
public class Platform implements OSPlatform
{
	private static final Platform DEFAULT_PLATFORM = new Platform();
	private static OSPlatform platform;

	public static String getOS()
	{
		return System.getProperty("os.name");
	}

	public static String getUser()
	{
		return System.getProperty("user.name");
	}

	public static String getHome()
	{
		return System.getProperty("user.home");
	}

	public static OSPlatform getPlatform()
	{
		if (platform == null)
		{
			String os = getOS().toLowerCase();
			if (os.contains("linux"))
			{
				platform = new LinuxPlatform();
			}
			else if (os.contains("windows"))
			{
				platform = new WindowsPlatform();
			}
			else if (os.contains("mac"))
			{
				platform = new MacPlatform();
			}
			else
			{
				platform = DEFAULT_PLATFORM;
			}
		}
		return platform;
	}

	@Override
	public Path getAppDataDirectory()
	{
		return Paths.get(getHome(), Application.instance().getName());
	}
}
