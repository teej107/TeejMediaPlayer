package com.teej107.mediaplayer.swing.action;

import com.teej107.mediaplayer.io.ApplicationPreferences;
import com.teej107.mediaplayer.server.TeejMediaServer;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by teej107 on 4/27/2017.
 */
public class ServerToggleAction extends AbstractAction
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
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		if (mediaServer.isRunning())
		{
			mediaServer.stop();
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

			}
			mediaServer.start();
		}
		putValue(Action.NAME, (mediaServer.isRunning() ? "Stop" : "Start") + " Server");
	}
}
