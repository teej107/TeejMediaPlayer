package com.teej107.mediaplayer.server;

import com.eclipsesource.v8.JavaCallback;
import com.eclipsesource.v8.JavaVoidCallback;
import com.teej107.mediaplayer.Application;
import com.teej107.mediaplayer.io.ApplicationPreferences;

import java.util.*;

/**
 * Created by teej107 on 4/22/2017.
 */
public class TeejMediaServer implements Runnable
{
	private NodeRuntime runtime;
	private ApplicationPreferences applicationPreferences;
	private Map<String, JavaCallback> javaCallbacks;
	private Map<String, JavaVoidCallback> javaVoidCallbacks;
	private final Object collectionLock;

	public TeejMediaServer(Application application)
	{
		this.applicationPreferences = application.getApplicationPreferences();
		this.runtime = new NodeRuntime(this, application.getApplicationPreferences().getServerRootDirectory(), false);
		this.javaCallbacks = new HashMap<>();
		this.javaVoidCallbacks = new HashMap<>();
		this.collectionLock = new Object();
		application.addShutdownHook(this, 1410);

		addJavaCallback("getPort", (JavaCallback) (v8Object, v8Array) -> getPort());
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
		return applicationPreferences.getServerPort();
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
