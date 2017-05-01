package com.teej107.mediaplayer.swing.listener;

import com.teej107.mediaplayer.server.TeejMediaServer;
import com.teej107.mediaplayer.swing.ServerPanel;
import com.teej107.mediaplayer.util.SwingEDT;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.concurrent.ExecutorService;

/**
 * Created by teej107 on 4/30/2017.
 */
public class InstallServerMouseListener implements MouseListener
{
	private ExecutorService service;
	private ServerPanel serverPanel;
	private TeejMediaServer mediaServer;

	public InstallServerMouseListener(ServerPanel serverPanel, TeejMediaServer mediaServer, ExecutorService service)
	{
		this.mediaServer = mediaServer;
		this.serverPanel = serverPanel;
		this.service = service;
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		if (((JComponent) e.getSource()).isEnabled())
		{
			if(!mediaServer.isInstalling() && !mediaServer.isInstalled())
			{
				service.submit(() -> mediaServer.install());
			}
			SwingEDT.invoke(() -> serverPanel.update());
		}
	}

	@Override
	public void mousePressed(MouseEvent e)
	{

	}

	@Override
	public void mouseReleased(MouseEvent e)
	{

	}

	@Override
	public void mouseEntered(MouseEvent e)
	{

	}

	@Override
	public void mouseExited(MouseEvent e)
	{

	}
}
