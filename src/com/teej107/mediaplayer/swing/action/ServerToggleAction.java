package com.teej107.mediaplayer.swing.action;

import com.teej107.mediaplayer.Application;
import com.teej107.mediaplayer.server.TeejMediaServer;

import javax.swing.*;
import java.awt.event.ActionEvent;

/**
 * Created by teej107 on 4/27/2017.
 */
public class ServerToggleAction extends AbstractAction
{
	public ServerToggleAction()
	{
		super((Application.instance().getMediaServer().isRunning() ? "Stop" : "Start") + " Server");
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		TeejMediaServer mediaServer = Application.instance().getMediaServer();
		if(mediaServer.isRunning())
		{
			mediaServer.stop();
		}
		else
		{
			mediaServer.start();
		}
		putValue(Action.NAME, (Application.instance().getMediaServer().isRunning() ? "Stop" : "Start") + " Server");
	}
}
