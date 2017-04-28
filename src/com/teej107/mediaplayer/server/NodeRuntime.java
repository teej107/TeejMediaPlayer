package com.teej107.mediaplayer.server;

import com.eclipsesource.v8.*;

import java.io.File;
import java.io.IOException;
import java.net.*;
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

	public NodeRuntime(TeejMediaServer mediaServer, Path root, boolean copy)
	{
		this.mediaServer = mediaServer;
		if (copy)
		{
			copyNode(root);
		}
		this.indexjs = new File(root.toFile(), "index.js");
	}

	private void copyNode(Path root)
	{
		try
		{
			URI uri = getClass().getResource("/com/teej107/mediaplayer/server/web").toURI();
			Files.walkFileTree(Paths.get(uri), new NodeFileVisitor(root));
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
		if(nodeThread == null)
		{
			nodeThread = new Thread(this);
		}
		nodeThread.start();
	}

	public void stop()
	{
		if(nodeThread == null)
			return;

		nodeThread.interrupt();
		nodeThread = null;
		try
		{
			URL url = new URL("http://localhost:" + mediaServer.getPort());
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.connect();
			connection.disconnect();
		}
		catch (IOException e)
		{
			System.err.println(e.getMessage());
		}
	}

	public boolean isRunning()
	{
		return nodeThread != null;
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
