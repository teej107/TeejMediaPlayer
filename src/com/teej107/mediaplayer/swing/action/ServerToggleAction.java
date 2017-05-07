package com.teej107.mediaplayer.swing.action;

import com.teej107.mediaplayer.io.ApplicationPreferences;
import com.teej107.mediaplayer.server.ServerStateListener;
import com.teej107.mediaplayer.server.TeejMediaServer;
import com.teej107.mediaplayer.util.SwingEDT;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by teej107 on 4/27/2017.
 */
public class ServerToggleAction extends AbstractAction implements ServerStateListener
{
	private ApplicationPreferences applicationPreferences;
	private TeejMediaServer mediaServer;
	private JTextField textField;

	public ServerToggleAction(ApplicationPreferences applicationPreferences, TeejMediaServer mediaServer, JTextField textField)
	{
		super((mediaServer.isRunning() ? "Stop" : "Start") + " Server");
		this.applicationPreferences = applicationPreferences;
		this.mediaServer = mediaServer;
		this.textField = textField;
		mediaServer.addServerStateListener(this);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (mediaServer.isRunning())
		{
			putValue(Action.NAME, "Stopping Server...");
			SwingEDT.invokeOutside(() -> mediaServer.stop());
		}
		else
		{
			try
			{
				int textNum = Integer.parseInt(textField.getText());
				if (textNum != applicationPreferences.getServerPort())
				{
					if (JOptionPane
							.showConfirmDialog(null, "Do you want to run the server on port " + textNum + "?", "Change Port",
									JOptionPane.YES_NO_OPTION) == JOptionPane.YES_OPTION)
					{
						applicationPreferences.setServerPort(textNum);
					}
					else
					{
						textField.setText(Integer.toString(mediaServer.getPort()));
					}
				}
			}
			catch (NumberFormatException e1)
			{
				textField.setText(Integer.toString(applicationPreferences.getServerPort()));
			}
			mediaServer.start();
			putValue(Action.NAME, "Stop Server");
		}

	}

	@Override
	public void onStart()
	{

	}

	@Override
	public void onStop()
	{
		SwingEDT.invoke(() -> putValue(Action.NAME, "Start Server"));
	}

	@Override
	public void onInstalling()
	{

	}

	@Override
	public void onInstalled()
	{

	}
}
