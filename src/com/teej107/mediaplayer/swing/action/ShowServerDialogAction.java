package com.teej107.mediaplayer.swing.action;

import com.teej107.mediaplayer.Application;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by teej107 on 5/1/2017.
 */
public class ShowServerDialogAction extends AbstractAction
{
	private Application application;

	public ShowServerDialogAction(Application application)
	{
		super("Server");
		this.application = application;
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		application.getServerDialog().setVisible(true);
	}
}
