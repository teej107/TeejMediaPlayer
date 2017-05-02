package com.teej107.mediaplayer.server;

import com.eclipsesource.v8.JavaCallback;
import com.eclipsesource.v8.JavaVoidCallback;
import com.teej107.mediaplayer.Application;
import com.teej107.mediaplayer.io.ApplicationPreferences;
import com.teej107.mediaplayer.media.audio.DatabaseSong;
import com.teej107.mediaplayer.util.Version;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.net.URI;
import java.nio.file.*;
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
	private Version embeddedVersion, installedVersion;
	private Collection<ServerStateListener> serverStateListeners;

	public TeejMediaServer(Application application)
	{
		this.applicationPreferences = application.getApplicationPreferences();
		this.runtime = new NodeRuntime(this, applicationPreferences.getServerRootDirectory(), false);
		this.javaCallbacks = new HashMap<>();
		this.javaVoidCallbacks = new HashMap<>();
		this.collectionLock = new Object();
		this.serverStateListeners = new HashSet<>();

		readServerVersion();

		addJavaCallback("getPort", (JavaCallback) (v8Object, v8Array) -> getPort());
		addJavaCallback("j_getFile", (JavaCallback) (v8Object, v8Array) ->
		{
			if (v8Array.length() > 0)
			{
				URI uri = Paths.get(v8Array.getString(0)).toUri();
				DatabaseSong song = application.getDatabaseManager().getSongByURI(uri);
				if (song != null)
					return new File(uri).getAbsolutePath();
			}
			return null;
		});

		application.addShutdownHook(this, 1410);
	}

	private void readServerVersion()
	{
		try
		{
			JSONParser parser = new JSONParser();
			JSONObject jsonObject = (JSONObject) parser.parse(
					new InputStreamReader(getClass().getResourceAsStream("/com/teej107/mediaplayer/server/web/package.json")));
			embeddedVersion = new Version((String) jsonObject.get("version"));
			Path packagejson = applicationPreferences.getServerRootDirectory().resolve("package.json");
			if (runtime.exists() && Files.exists(packagejson))
			{
				jsonObject = (JSONObject) parser.parse(new InputStreamReader(Files.newInputStream(packagejson)));
				installedVersion = new Version((String) jsonObject.get("version"));
			}
			else
			{
				installedVersion = new Version();
			}
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
	}

	public boolean isInstalled()
	{
		return runtime.exists() && embeddedVersion.equals(installedVersion);
	}

	public void install()
	{
		for(ServerStateListener listener : serverStateListeners)
		{
			listener.onInstalling();
		}
		runtime.copyNode();
		readServerVersion();
		for(ServerStateListener listener : serverStateListeners)
		{
			listener.onInstalled();
		}
	}

	public boolean isInstalling()
	{
		return runtime.isCopying();
	}

	public Version getEmbeddedVersion()
	{
		return embeddedVersion;
	}

	public Version getInstalledVersion()
	{
		return installedVersion;
	}

	public Object getLock()
	{
		return collectionLock;
	}

	public void start()
	{
		runtime.start();
		for(ServerStateListener listener : serverStateListeners)
		{
			listener.onStart();
		}
	}

	public void stop()
	{
		runtime.stop();
		for(ServerStateListener listener : serverStateListeners)
		{
			listener.onStop();
		}
	}

	public boolean isRunning()
	{
		return runtime.isRunning();
	}

	public boolean addServerStateListener(ServerStateListener listener)
	{
		return serverStateListeners.add(listener);
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
