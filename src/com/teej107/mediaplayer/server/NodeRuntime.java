package com.teej107.mediaplayer.server;

import com.eclipsesource.v8.*;
import com.teej107.mediaplayer.Application;
import com.teej107.mediaplayer.io.CounterFileVisitor;
import com.teej107.mediaplayer.swing.ApplicationStatusBar;
import com.teej107.mediaplayer.util.SwingEDT;

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
	private Path root;
	private File indexjs;
	private volatile boolean isCopying;

	public NodeRuntime(TeejMediaServer mediaServer, Path root, boolean copy)
	{
		this.mediaServer = mediaServer;
		this.root = root;
		this.isCopying = false;
		if (copy)
		{
			copyNode();
		}
		this.indexjs = new File(root.toFile(), "index.js");
	}

	public void copyNode()
	{
		isCopying = true;
		try
		{
			URI uri = getClass().getResource("/com/teej107/mediaplayer/server/web").toURI();
			Path path = Paths.get(uri);
			CounterFileVisitor counterFileVisitor = new CounterFileVisitor().walk(path);
			ApplicationStatusBar statusBar = Application.instance().getApplicationFrame().getApplicationStatusBar();
			SwingEDT.invoke(() ->
			{
				statusBar.getProgressBar().setMaximum(counterFileVisitor.getCount());
				statusBar.getProgressBar().setValue(0);
				statusBar.getProgressLabel().setText("Installing Server...");
				statusBar.setProgressVisible(true);
			});
			Files.walkFileTree(path,
					new NodeFileVisitor(root, (progress) -> SwingEDT.invoke(() -> statusBar.getProgressBar().setValue(progress))));
			SwingEDT.invoke(() -> statusBar.setProgressVisible(false));
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

	public void start()
	{
		if (!exists())
			return;

		if (nodeThread == null)
		{
			nodeThread = new Thread(this);
		}
		nodeThread.start();
	}

	public void stop()
	{
		if (nodeThread == null)
			return;

		nodeThread.interrupt();
		nodeThread = null;
		try
		{
			URL url = new URL("http://localhost:" + mediaServer.getPort());
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setRequestMethod("GET");
			connection.connect();
			connection.setConnectTimeout(1);
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
