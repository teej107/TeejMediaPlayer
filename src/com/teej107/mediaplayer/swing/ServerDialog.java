package com.teej107.mediaplayer.swing;

import com.teej107.mediaplayer.Application;

import javax.swing.*;

/**
 * Created by teej107 on 4/27/2017.
 */
public class ServerDialog extends JDialog
{
	public ServerDialog()
	{
		super(Application.instance().getApplicationFrame(), "Server");
		setContentPane(new ServerPanel(Application.instance().getApplicationPreferences()));
		setLocationRelativeTo(Application.instance().getApplicationFrame());
		setSize(225, 140);
		dispose();
	}
}
