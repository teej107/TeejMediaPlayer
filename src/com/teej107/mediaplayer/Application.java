package com.teej107.mediaplayer;

import com.teej107.mediaplayer.io.ApplicationPreferences;
import com.teej107.mediaplayer.io.db.DatabaseManager;
import com.teej107.mediaplayer.media.AudioPlayer;
import com.teej107.mediaplayer.media.volume.VolumeManager;
import com.teej107.mediaplayer.platform.Platform;
import com.teej107.mediaplayer.swing.ApplicationFrame;
import com.teej107.mediaplayer.util.ComparableObject;
import com.teej107.mediaplayer.util.SwingEDT;
import com.teej107.mediaplayer.server.NodeRuntime;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

/**
 * A singleton style class used for managing application wide objects
 */
public class Application implements Comparator<ComparableObject<Runnable>>
{
	private static final Application APPLICATION = new Application();
	private SortedSet<ComparableObject<Runnable>> shutdownHooks;
	private ApplicationPreferences applicationPreferences;
	private ApplicationFrame applicationFrame;
	private DatabaseManager databaseManager;
	private VolumeManager volumeManager;
	private AudioPlayer audioPlayer;
	private NodeRuntime nodeRuntime;

	private Application()
	{
		this.shutdownHooks = new TreeSet<>(this);
		this.applicationPreferences = new ApplicationPreferences(this);
		this.volumeManager = new VolumeManager(applicationPreferences.getPlayerState().getVolume());
		this.audioPlayer = new AudioPlayer(volumeManager);
	}

	protected Application init()
	{
		try
		{
			Files.createDirectories(Platform.getPlatform().getAppDataDirectory());
			this.databaseManager = new DatabaseManager(Paths.get(Platform.getPlatform().getAppDataDirectory().toString(), "storage.db"));
			this.nodeRuntime = new NodeRuntime(this);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage());
			exit();
		}
		nodeRuntime.start();
		return this;
	}

	protected void createGui()
	{
		SwingEDT.invoke(() ->
		{
			this.applicationFrame = new ApplicationFrame(this);
			this.applicationFrame.setVisible(true);
		});
	}

	public static Application instance()
	{
		return APPLICATION;
	}

	/**
	 * Get the application getTitle
	 *
	 * @return application name
	 */
	public String getName()
	{
		return "Teej Media Player";
	}

	public ApplicationPreferences getApplicationPreferences()
	{
		return applicationPreferences;
	}

	public VolumeManager getVolumeManager()
	{
		return volumeManager;
	}

	public AudioPlayer getAudioPlayer()
	{
		return audioPlayer;
	}

	public DatabaseManager getDatabaseManager()
	{
		return databaseManager;
	}

	/**
	 * Add a shutdown hook to run code when the application exits
	 *
	 * @param runnable Runnable object
	 * @param priority priority of when the Runnable run method should be invoked. Lower integers run first. Higher integers run last.
	 */
	public boolean addShutdownHook(Runnable runnable, int priority)
	{
		return shutdownHooks.add(new ComparableObject<>(runnable, priority));
	}

	/**
	 * Invokes the shutdown hooks then quits the application
	 */
	public void exit()
	{
		for (ComparableObject<Runnable> obj : shutdownHooks)
		{
			obj.getObject().run();
		}
		nodeRuntime.stop();
		System.exit(0);
	}

	/**
	 * Used for sorting shutdown hooks from the interface Comparator
	 */
	@Override
	public int compare(ComparableObject<Runnable> t1, ComparableObject<Runnable> t2)
	{
		return t1.getPriority() - t2.getPriority();
	}
}
