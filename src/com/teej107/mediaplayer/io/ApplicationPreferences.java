package com.teej107.mediaplayer.io;

import com.teej107.mediaplayer.Application;
import com.teej107.mediaplayer.platform.Platform;
import com.teej107.mediaplayer.util.Response;

import javax.swing.*;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.prefs.Preferences;

/**
 * Preferences for the Application
 */
public class ApplicationPreferences implements Runnable
{
	private static final String WINDOW_STATE = "window-state";
	private static final String PLAYER_STATE = "player-state";
	private static final String MUSIC_ROOT_DIR = "music-root-directory";

	private Preferences prefs;
	private WindowState windowState;
	private PlayerState playerState;

	public ApplicationPreferences(Application application)
	{
		this.prefs = Preferences.userRoot().node(application.getName());

		windowState = new WindowState(getWindowStateRawData());
		Response windowResponse = windowState.load();
		if (windowResponse.getStatus() == Response.ERROR)
		{
			System.out.println(windowResponse.getMessageAsString());
			JOptionPane.showMessageDialog(null, windowResponse.getMessageAsString(), WINDOW_STATE, JOptionPane.ERROR_MESSAGE);
		}

		playerState = new PlayerState(getPlayerStateRawData());
		Response playerResponce = playerState.load();

		application.addShutdownHook(this, Integer.MAX_VALUE);
	}

	private String getWindowStateRawData()
	{
		return prefs.get(WINDOW_STATE, null);
	}

	private void setWindowStateRawData(String rawData)
	{
		prefs.put(WINDOW_STATE, rawData);
	}

	private String getPlayerStateRawData()
	{
		return prefs.get(PLAYER_STATE, null);
	}

	private void setPlayerStateRawData(String rawData)
	{
		prefs.put(PLAYER_STATE, rawData);
	}

	/**
	 * Get the WindowState object
	 *
	 * @return WindowState object
	 */
	public WindowState getWindowState()
	{
		return windowState;
	}

	public PlayerState getPlayerState()
	{
		return playerState;
	}

	public Path getMusicRootDirectory()
	{
		String path = prefs.get(MUSIC_ROOT_DIR, null);
		return path == null ? Platform.getPlatform().getAppDataDirectory().resolve("music") : Paths.get(path);
	}

	public Path getServerRootDirectory()
	{
		return Platform.getPlatform().getAppDataDirectory().resolve("server");
	}

/*	public void setMusicRootDirectory(Path path)
	{
		prefs.put(MUSIC_ROOT_DIR, path.toString());
	}*/

	/**
	 * Shutdown hook
	 */
	@Override
	public void run()
	{
		try
		{
			setWindowStateRawData(windowState.call());
			setPlayerStateRawData(playerState.call());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
