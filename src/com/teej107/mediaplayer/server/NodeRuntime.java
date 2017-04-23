package com.teej107.mediaplayer.server;

import com.eclipsesource.v8.*;
import com.sun.istack.internal.Nullable;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.Map;

/**
 * Created by teej107 on 4/22/17.
 */
public class NodeRuntime implements Runnable
{
	private TeejMediaServer mediaServer;
	private Thread nodeThread;
	private File indexjs;

	public NodeRuntime(TeejMediaServer mediaServer, @Nullable Path root)
	{
		this.mediaServer = mediaServer;
		this.nodeThread = new Thread(this);
		if(root != null)
		{
			copyNode(root);
		}
	}

	private void copyNode(Path root)
	{
		try
		{
			URI uri = getClass().getResource("/com/teej107/mediaplayer/server/web").toURI();
			Files.walkFileTree(Paths.get(uri), new NodeFileVisitor(root));
			this.indexjs = new File(root.toFile(), "index.js");
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

	public boolean isRunning()
	{
		return !nodeThread.isInterrupted();
	}

	@Override
	public void run()
	{
		synchronized (mediaServer.getLock())
		{
			System.out.println("Starting Node server...");
			NodeJS nodejs = NodeJS.createNodeJS();
			for (Map.Entry<String, JavaVoidCallback> entry : mediaServer.getJavaVoidCallbacks())
			{
				nodejs.getRuntime().registerJavaMethod(entry.getValue(), entry.getKey());
			}
			for (Map.Entry<String, JavaCallback> entry : mediaServer.getJavaCallbacks())
			{
				nodejs.getRuntime().registerJavaMethod(entry.getValue(), entry.getKey());
			}

			nodejs.exec(indexjs);
			while (!Thread.currentThread().isInterrupted() && nodejs.isRunning())
			{
				nodejs.handleMessage();
			}
			nodejs.release();
			System.out.println("Node server released");
		}
	}
}
