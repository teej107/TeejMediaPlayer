package com.teej107.mediaplayer.swing;

import com.teej107.mediaplayer.Application;

import javax.swing.*;

/**
 * Created by teej107 on 5/31/17.
 */
public class ApplicationDialog extends JDialog
{
	public ApplicationDialog(Application application, JPanel panel, String title)
	{
		super(application.getApplicationFrame(), title);
		setLocationRelativeTo(application.getApplicationFrame());
		setLocationRelativeTo(application.getApplicationFrame());

		setContentPane(panel);
		setMinimumSize(panel.getMinimumSize());
		setMaximumSize(panel.getMaximumSize());
		setPreferredSize(panel.getPreferredSize());
	}
}
