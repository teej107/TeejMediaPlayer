package com.teej107.mediaplayer.swing;

import com.teej107.mediaplayer.Application;

import javax.swing.*;
import java.awt.*;

/**
 * Created by teej107 on 4/27/2017.
 */
public class ServerDialog extends JDialog
{
	public ServerDialog()
	{
		super(Application.instance().getApplicationFrame(), "Server");
		Application app = Application.instance();
		setContentPane(new ServerPanel(app.getApplicationPreferences(), app.getMediaServer(), app.getThreadService()));
		setLocationRelativeTo(Application.instance().getApplicationFrame());
		setMinimumSize(new Dimension(225, 220));
		dispose();
	}
}
