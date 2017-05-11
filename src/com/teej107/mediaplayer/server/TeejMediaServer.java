package com.teej107.mediaplayer.server;

import com.eclipsesource.v8.JavaCallback;
import com.eclipsesource.v8.JavaVoidCallback;
import com.teej107.mediaplayer.Application;
import com.teej107.mediaplayer.io.ApplicationPreferences;
import com.teej107.mediaplayer.media.audio.*;
import com.teej107.mediaplayer.util.*;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

/**
 * Created by teej107 on 4/22/2017.
 */
public class TeejMediaServer implements Runnable
{
	private NodeRuntime runtime;
	private ApplicationPreferences applicationPreferences;
	private ServerApi serverApi;
	private Map<String, JavaCallback> javaCallbacks;
	private Map<String, JavaVoidCallback> javaVoidCallbacks;
	private final Object collectionLock;
	private Version embeddedVersion, installedVersion;
	private Collection<ServerStateListener> serverStateListeners;

	public TeejMediaServer(Application application)
	{
		this.applicationPreferences = application.getApplicationPreferences();
		this.runtime = new NodeRuntime(this, applicationPreferences.getServerRootDirectory(), false);
		this.serverApi = new ServerApi(application);
		this.javaCallbacks = new HashMap<>();
		this.javaVoidCallbacks = new HashMap<>();
		this.collectionLock = new Object();
		this.serverStateListeners = new HashSet<>();

		readServerVersion();

		addJavaCallback("j_getPort", (JavaCallback) (v8Object, v8Array) -> getPort());
		addJavaCallback("j_getFile", (v8Object, v8Array) -> v8Array.length() > 0 ? serverApi.getSongFile(v8Array.getString(0)) : null);
		addJavaCallback("j_getSongJSON", (v8Object, v8Array) -> v8Array.length() > 0 ? serverApi.getSongJSON(v8Array.getString(0)) : null);
		addJavaCallback("j_getAlbumArt", (v8Object, v8Array) ->
		{
			if(v8Array.length() > 1)
			{
				String album = v8Array.getString(1);
				if(album.toLowerCase().endsWith(".jpg"))
				{
					album = album.substring(0, album.length() - 4);
				}

				List<DatabaseSong> list = application.getDatabaseManager().getAlbumByArtist(v8Array.getString(0), album);
				if(list.size() == 0)
					return null;
				Path path = application.getAlbumManager().getAlbumCoverPath(list.get(0));
				if(!Files.exists(path))
				{
					application.getAlbumManager().getAndSaveAlbumCover(list.get(0));
				}
				return path.toString();
			}
			return null;
		});
		addJavaCallback("j_getLibrary", (v8Object, v8Array) ->
		{
			Map map = new LinkedHashMap();
			for (DatabaseSong song : application.getDatabaseManager().getLibrary())
			{
				JSONObject songJson = Util.toJSONObject(song);
				map.put(songJson.get("path"), songJson);
			}
			return JSONObject.toJSONString(map);
		});

		application.addShutdownHook(this, 1410);
	}

	private void readServerVersion()
	{
		try
		{
			JSONParser parser = new JSONParser();
			InputStreamReader isr = new InputStreamReader(
					getClass().getResourceAsStream("/com/teej107/mediaplayer/server/web/package.json"));
			JSONObject jsonObject = (JSONObject) parser.parse(isr);
			isr.close();
			embeddedVersion = new Version((String) jsonObject.get("version"));
			Path packagejson = applicationPreferences.getServerRootDirectory().resolve("package.json");
			if (runtime.exists() && Files.exists(packagejson))
			{
				isr = new InputStreamReader(Files.newInputStream(packagejson));
				jsonObject = (JSONObject) parser.parse(isr);
				isr.close();
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

	public boolean install()
	{
		if (runtime.isCopying())
			return false;
		for (ServerStateListener listener : serverStateListeners)
		{
			listener.onInstalling();
		}
		runtime.copyNode();
		readServerVersion();
		for (ServerStateListener listener : serverStateListeners)
		{
			listener.onInstalled();
		}
		return true;
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
		if (runtime.start())
		{
			for (ServerStateListener listener : serverStateListeners)
			{
				listener.onStart();
			}
		}
	}

	protected void fireOnStop()
	{
		for (ServerStateListener listener : serverStateListeners)
		{
			listener.onStop();
		}
	}

	public void stop()
	{
		if (runtime.stop())
		{
			fireOnStop();
		}
	}

	public boolean isRunning()
	{
		return runtime.isRunning();
	}

	public boolean isStopping()
	{
		return runtime.isStopping();
	}

	public boolean addServerStateListener(ServerStateListener listener)
	{
		return serverStateListeners.add(listener);
	}

	@Override
	public void run()
	{
		SwingEDT.invokeOutside(() -> runtime.stop());
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
