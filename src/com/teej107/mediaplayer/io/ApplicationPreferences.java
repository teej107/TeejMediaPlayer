package com.teej107.mediaplayer.io;

import com.teej107.mediaplayer.Application;
import com.teej107.mediaplayer.platform.Platform;
import com.teej107.mediaplayer.util.Response;

import javax.swing.*;
import java.nio.file.Path;
import java.util.prefs.Preferences;

/**
 * Preferences for the Application
 */
public class ApplicationPreferences implements Runnable
{
	private static final String WINDOW_STATE = "window-state";
	private static final String PLAYER_STATE = "player-state";
	private static final String SERVER_PORT = "server-port";
	private static final String API_STATE = "api-state";

	private Preferences prefs;
	private WindowState windowState;
	private PlayerState playerState;
	private ApiState apiState;

	public ApplicationPreferences(Application application)
	{
		this.prefs = Preferences.userRoot().node(application.getName());

		this.windowState = new WindowState(getWindowStateRawData());
		displayIfError(windowState.load(), WINDOW_STATE);

		this.playerState = new PlayerState(getPlayerStateRawData());
		displayIfError(playerState.load(), PLAYER_STATE);

		this.apiState = new ApiState(getApiStateRawData());
		displayIfError(apiState.load(), API_STATE);

		application.addShutdownHook(this, Integer.MAX_VALUE);
	}

	private void displayIfError(Response response, String title)
	{
		if(response.getStatus() == Response.ERROR)
		{
			System.err.println(response.getMessageAsString());
			JOptionPane.showMessageDialog(null, response.getMessageAsString(), title, JOptionPane.ERROR_MESSAGE);
		}
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

	private String getApiStateRawData()
	{
		return prefs.get(API_STATE, null);
	}

	private void setApiStateRawData(String rawData)
	{
		prefs.put(API_STATE, rawData);
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

	public ApiState getApiState()
	{
		return apiState;
	}

	public Path getServerRootDirectory()
	{
		return Platform.getPlatform().getAppDataDirectory().resolve("server");
	}

	public Path getAlbumRootDirectory()
	{
		return Platform.getPlatform().getAppDataDirectory().resolve("Album Art");
	}

	public int getServerPort()
	{
		return prefs.getInt(SERVER_PORT, 1410);
	}

	public void setServerPort(int port)
	{
		prefs.putInt(SERVER_PORT, port);
	}

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
			setApiStateRawData(apiState.call());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
