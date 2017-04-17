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
	private static final String MUSIC_ROOT_DIR = "music-root-directory";

	private Preferences prefs;
	private WindowState windowState;

	public ApplicationPreferences(Application application)
	{
		this.prefs = Preferences.userRoot().node(application.getName());

		windowState = new WindowState(getWindowStateRawData());
		Response response = windowState.load();
		if (response.getStatus() == Response.ERROR)
		{
			System.out.println(response.getMessageAsString());
			JOptionPane.showMessageDialog(null, response.getMessageAsString(), WINDOW_STATE, JOptionPane.ERROR_MESSAGE);
		}

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

	/**
	 * Get the WindowState object
	 *
	 * @return WindowState object
	 */
	public WindowState getWindowState()
	{
		return windowState;
	}

	public Path getMusicRootDirectory()
	{
		String path = prefs.get(MUSIC_ROOT_DIR, null);
		return path == null ? Paths.get(Platform.getPlatform().getAppDataDirectory().toString(), "music") : Paths.get(path);
	}

	/**
	 * Shutdown hook
	 */
	@Override
	public void run()
	{
		setWindowStateRawData(windowState.toString());
	}
}
