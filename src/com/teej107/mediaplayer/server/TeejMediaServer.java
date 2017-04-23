package com.teej107.mediaplayer.server;

import com.eclipsesource.v8.*;
import com.teej107.mediaplayer.Application;

import java.util.*;

/**
 * Created by teej107 on 4/22/2017.
 */
public class TeejMediaServer implements Runnable
{
	private NodeRuntime runtime;
	private int port;
	private Map<String, JavaCallback> javaCallbacks;
	private Map<String, JavaVoidCallback> javaVoidCallbacks;
	private final Object collectionLock;

	public TeejMediaServer(Application application)
	{
		this.runtime = new NodeRuntime(this, application.getApplicationPreferences().getServerRootDirectory());
		this.port = application.getApplicationPreferences().getServerPort();
		this.javaCallbacks = new HashMap<>();
		this.javaVoidCallbacks = new HashMap<>();
		this.collectionLock = new Object();
		application.addShutdownHook(this, 1410);

		addJavaCallback("getPort", (JavaCallback) (v8Object, v8Array) -> port);
	}

	public Object getLock()
	{
		return collectionLock;
	}

	public void start()
	{
		runtime.start();
	}

	public void stop()
	{
		runtime.stop();
	}

	public boolean isRunning()
	{
		return runtime.isRunning();
	}

	@Override
	public void run()
	{
		runtime.stop();
	}

	public int getPort()
	{
		return port;
	}

	public Collection<Map.Entry<String, JavaCallback>> getJavaCallbacks()
	{
		return javaCallbacks.entrySet();
	}

	public Collection<Map.Entry<String, JavaVoidCallback>> getJavaVoidCallbacks()
	{
		return javaVoidCallbacks.entrySet();
	}

	public boolean addJavaCallback(String name, JavaCallback callback)
	{
		synchronized (collectionLock)
		{
			return javaCallbacks.put(name, callback) == null;
		}
	}

	public boolean addJavaCallback(String name, JavaVoidCallback callback)
	{
		synchronized (collectionLock)
		{
			return javaVoidCallbacks.put(name, callback) == null;
		}
	}
}
