package com.teej107.mediaplayer.platform.os;

import java.nio.file.Path;

/**
 * Created by teej107 on 4/15/17.
 */
public interface OSPlatform
{
	Path getAppDataDirectory();
	String getTerminate();
}
