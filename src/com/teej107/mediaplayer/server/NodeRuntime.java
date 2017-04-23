package com.teej107.mediaplayer.server;

import com.eclipsesource.v8.NodeJS;
import com.teej107.mediaplayer.Application;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by teej107 on 4/22/17.
 */
public class NodeRuntime implements Runnable
{
	private Thread nodeThread;
	private File indexjs;

	public NodeRuntime(Application application)
	{
		this.nodeThread = new Thread(this);
		try
		{
			URI uri = getClass().getResource("/com/teej107/mediaplayer/server/web").toURI();
			Files.walkFileTree(Paths.get(uri), new NodeFileVisitor(application.getApplicationPreferences().getServerRootDirectory()));
			this.indexjs = new File(application.getApplicationPreferences().getServerRootDirectory().toFile(), "index.js");
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
		nodeThread.start();
	}

	public void stop()
	{
		nodeThread.interrupt();
	}

	@Override
	public void run()
	{
		System.out.println("Starting Node server...");
		NodeJS nodejs = NodeJS.createNodeJS();
		nodejs.exec(indexjs);
		while (!Thread.currentThread().isInterrupted() && nodejs.isRunning())
		{
			nodejs.handleMessage();
		}
		nodejs.release();
		System.out.println("Node server released");
	}
}
