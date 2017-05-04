package com.teej107.mediaplayer.swing;

import com.teej107.mediaplayer.Application;

import javax.swing.*;
import java.awt.*;

/**
 * Created by teej107 on 4/27/2017.
 */
public class ServerDialog extends JDialog
{
	private ServerPanel serverPanel;

	public ServerDialog(Application application)
	{
		super(application.getApplicationFrame(), "Server");
		this.serverPanel = new ServerPanel(application.getApplicationPreferences(), application.getMediaServer(),
				application.getThreadService());
		setContentPane(serverPanel);
		setLocationRelativeTo(application.getApplicationFrame());
		setMinimumSize(new Dimension(250, 230));
	}
}
