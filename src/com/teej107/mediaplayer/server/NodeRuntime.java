package com.teej107.mediaplayer.server;

import com.eclipsesource.v8.*;
import com.teej107.mediaplayer.io.CounterFileVisitor;
import com.teej107.mediaplayer.util.ProgressListener;
import com.teej107.mediaplayer.util.Util;

import java.io.File;
import java.io.IOException;
import java.net.*;
import java.nio.file.*;
import java.util.*;

/**
 * Created by teej107 on 4/22/17.
 */
public class NodeRuntime implements Runnable
{
	private TeejMediaServer mediaServer;
	private volatile Thread nodeThread;
	private Path root;
	private File indexjs;
	private volatile boolean isCopying, stopping;
	private volatile String shutdownKey;
	private Collection<ProgressListener> installProgressListener;

	public NodeRuntime(TeejMediaServer mediaServer, Path root)
	{
		this.mediaServer = mediaServer;
		this.root = root;
		this.isCopying = false;
		this.stopping = false;
		this.installProgressListener = new HashSet<>();
		this.indexjs = new File(root.toFile(), "index.js");
	}

	public boolean addInstallProgressListener(ProgressListener listener)
	{
		return installProgressListener.add(listener);
	}

	public void copyNode()
	{
		isCopying = true;
		try
		{
			URI uri = getClass().getResource("/com/teej107/mediaplayer/server/web").toURI();
			Path path = Paths.get(uri);
			CounterFileVisitor counterFileVisitor = new CounterFileVisitor().walk(path);
			final int maxCount = counterFileVisitor.getCount();
			Files.walkFileTree(path,
					new NodeFileVisitor(root, (min, value, max) ->
					{
						for (ProgressListener listener : installProgressListener)
						{
							listener.onProgressChange(0, value, maxCount);
						}
					}));
		}
		catch (URISyntaxException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		isCopying = false;
	}

	public boolean isCopying()
	{
		return isCopying;
	}

	public boolean exists()
	{
		return indexjs.exists();
	}

	public boolean start()
	{
		if (!exists() || isRunning())
			return false;

		if (nodeThread == null)
		{
			nodeThread = new Thread(this);
		}
		nodeThread.start();
		return true;
	}

	public boolean isStopping()
	{
		return stopping;
	}

	public boolean stop()
	{
		if (nodeThread == null || stopping)
			return false;

		stopping = true;
		try
		{
			URL url = new URL("http://localhost:" + mediaServer.getPort() + "/" + shutdownKey);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.getResponseCode();
			connection.getInputStream().close();

			//Force close the thread
			nodeThread.interrupt();

			while (shutdownKey != null)
			{
				Thread.yield();
				try
				{
					Thread.currentThread().sleep(100);
				}
				catch (InterruptedException e)
				{
					e.printStackTrace();
				}
			}
		}
		catch (IOException e)
		{
			System.err.println(e.getMessage());
		}
		stopping = false;
		nodeThread = null;
		return true;
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
			shutdownKey = Util.getRandomAlphaNumeric(64);
			nodejs.getRuntime().registerJavaMethod((JavaCallback) (v8Object, v8Array) -> shutdownKey, "j_shutdownKey");
			nodejs.exec(indexjs);
			while (!Thread.currentThread().isInterrupted() && nodejs.isRunning())
			{
				nodejs.handleMessage();
			}
			nodejs.release();
			System.out.println("Node server released");
			shutdownKey = null;
			if (nodeThread != null)
			{
				nodeThread = null;
				mediaServer.fireOnStop();
			}
		}
	}
}
