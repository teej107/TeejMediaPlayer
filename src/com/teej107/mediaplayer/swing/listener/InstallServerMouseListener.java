package com.teej107.mediaplayer.swing.listener;

import com.teej107.mediaplayer.server.ServerStateListener;
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
public class InstallServerMouseListener implements MouseListener, ServerStateListener
{
	private AbstractButton abstractButton;
	private ExecutorService service;
	private ServerPanel serverPanel;
	private TeejMediaServer mediaServer;

	public InstallServerMouseListener(AbstractButton abstractButton, ServerPanel serverPanel, TeejMediaServer mediaServer,
			ExecutorService service)
	{
		this.abstractButton = abstractButton;
		this.mediaServer = mediaServer;
		this.serverPanel = serverPanel;
		this.service = service;
		mediaServer.addServerStateListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		if (abstractButton.isEnabled() && (!mediaServer.isInstalling() && !mediaServer.isInstalled()))
		{
			service.submit(() -> mediaServer.install());
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

	@Override
	public void onStart()
	{

	}

	@Override
	public void onStop()
	{

	}

	@Override
	public void onInstalling()
	{
		SwingEDT.invoke(() -> abstractButton.setText("Installing..."));
	}

	@Override
	public void onInstalled()
	{
		SwingEDT.invoke(() ->
		{
			abstractButton.setText("Install");
			serverPanel.update();
		});
	}
}
