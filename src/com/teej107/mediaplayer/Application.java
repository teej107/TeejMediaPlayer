package com.teej107.mediaplayer;

import com.teej107.mediaplayer.io.AlbumManager;
import com.teej107.mediaplayer.io.ApplicationPreferences;
import com.teej107.mediaplayer.io.db.DatabaseManager;
import com.teej107.mediaplayer.media.AudioPlayer;
import com.teej107.mediaplayer.media.volume.VolumeManager;
import com.teej107.mediaplayer.platform.Platform;
import com.teej107.mediaplayer.server.TeejMediaServer;
import com.teej107.mediaplayer.swing.ApplicationFrame;
import com.teej107.mediaplayer.swing.ServerDialog;
import com.teej107.mediaplayer.util.ComparableObject;
import com.teej107.mediaplayer.util.SwingEDT;

import javax.swing.*;
import java.io.IOException;
import java.nio.file.Files;
import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * A singleton style class used for managing application wide objects
 */
public class Application implements Comparator<ComparableObject<Runnable>>
{
	private static final Application APPLICATION = new Application();
	private SortedSet<ComparableObject<Runnable>> shutdownHooks;
	private ApplicationPreferences applicationPreferences;
	private ApplicationFrame applicationFrame;
	private ServerDialog serverDialog;
	private DatabaseManager databaseManager;
	private VolumeManager volumeManager;
	private AudioPlayer audioPlayer;
	private TeejMediaServer mediaServer;
	private AlbumManager albumManager;
	private ExecutorService threadService;

	private Application()
	{
		this.shutdownHooks = new TreeSet<>(this);
		this.applicationPreferences = new ApplicationPreferences(this);
		this.volumeManager = new VolumeManager(applicationPreferences.getPlayerState().getVolume());
		this.audioPlayer = new AudioPlayer(volumeManager);
		this.albumManager = new AlbumManager(applicationPreferences);
		this.threadService = Executors.newCachedThreadPool();
	}

	protected Application init()
	{
		try
		{
			Files.createDirectories(Platform.getPlatform().getAppDataDirectory());
			this.databaseManager = new DatabaseManager(Platform.getPlatform().getAppDataDirectory().resolve("storage.db"));
			this.mediaServer = new TeejMediaServer(this);
		}
		catch (IOException e)
		{
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, e.getMessage());
			exit();
		}
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

	public ApplicationFrame getApplicationFrame()
	{
		return applicationFrame;
	}

	public AlbumManager getAlbumManager()
	{
		return albumManager;
	}

	public TeejMediaServer getMediaServer()
	{
		return mediaServer;
	}

	public DatabaseManager getDatabaseManager()
	{
		return databaseManager;
	}

	public ExecutorService getThreadService()
	{
		return threadService;
	}

	public ServerDialog getServerDialog()
	{
		if (serverDialog == null)
		{
			this.serverDialog = new ServerDialog(this);
		}
		return serverDialog;
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
	 * @return returns false if user prevented application exit. Otherwise returns *quits*
	 */
	public boolean exit()
	{
		if (mediaServer.isRunning() && !mediaServer.isStopping())
		{
			if (JOptionPane.showConfirmDialog(applicationFrame, "The server is currently running. Do you want to kill the server?",
					Platform.getPlatform().getTerminate() + "?", JOptionPane.YES_NO_OPTION) == JOptionPane.NO_OPTION)
			{
				applicationFrame.setExtendedState(JFrame.ICONIFIED);
				return false;
			}
		}
		for (ComparableObject<Runnable> obj : shutdownHooks)
		{
			obj.getObject().run();
		}
		System.exit(0);
		return true;
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
