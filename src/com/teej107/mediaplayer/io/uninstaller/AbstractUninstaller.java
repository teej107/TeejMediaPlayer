package com.teej107.mediaplayer.io.uninstaller;

import com.teej107.mediaplayer.Application;
import com.teej107.mediaplayer.util.Response;

import java.io.IOException;

/**
 * Created by teej107 on 5/31/17.
 */
public abstract class AbstractUninstaller
{
	private String name;
	protected Application application;

	public AbstractUninstaller(Application application, String name)
	{
		this.application = application;
		this.name = name;
	}

	public String getName()
	{
		return name;
	}

	public abstract Response uninstall() throws IOException;
}
