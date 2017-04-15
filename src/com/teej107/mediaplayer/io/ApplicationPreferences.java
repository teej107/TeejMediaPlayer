package com.teej107.mediaplayer.io;

import com.teej107.mediaplayer.Application;
import com.teej107.mediaplayer.util.Response;

import java.util.prefs.Preferences;

/**
 * Preferences for the Application
 */
public class ApplicationPreferences implements Runnable
{
	private static final String WINDOW_STATE = "window-state";

	private Preferences prefs;
	private WindowState windowState;

	public ApplicationPreferences(Application application)
	{
		this.prefs = Preferences.userRoot().node(application.getName());

		windowState = new WindowState(getWindowStateRawData());
		Response response = windowState.load();
		if(response.getStatus() == Response.ERROR)
		{
			System.out.println(response.getMessageAsString());
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
	 * @return WindowState object
	 */
	public WindowState getWindowState()
	{
		return windowState;
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
