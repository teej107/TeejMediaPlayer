package com.teej107.mediaplayer.server;

import com.eclipsesource.v8.*;
import com.teej107.mediaplayer.Application;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.stream.Stream;

/**
 * Created by teej107 on 4/22/17.
 */
public class NodeRuntime
{
	private NodeJS nodejs;

	public NodeRuntime(Application application)
	{
		this.nodejs = NodeJS.createNodeJS();

		try
		{
			URI uri = getClass().getResource("/com/teej107/mediaplayer/server/web").toURI();
			Files.walkFileTree(Paths.get(uri), new NodeFileVisitor(application.getApplicationPreferences().getServerRootDirectory()));
		}
		catch (URISyntaxException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void start()
	{

	}

	public void stop()
	{

	}
}
