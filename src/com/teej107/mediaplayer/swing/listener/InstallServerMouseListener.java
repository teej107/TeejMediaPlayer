package com.teej107.mediaplayer.swing.listener;

import com.teej107.mediaplayer.server.ServerStateListener;
import com.teej107.mediaplayer.server.TeejMediaServer;
import com.teej107.mediaplayer.swing.ServerPanel;
import com.teej107.mediaplayer.util.SwingEDT;

import javax.swing.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by teej107 on 4/30/2017.
 */
public class InstallServerMouseListener implements MouseListener, ServerStateListener
{
	private static final String REINSTALL = "Reinstall";

	private AbstractButton abstractButton;
	private ServerPanel serverPanel;
	private TeejMediaServer mediaServer;
	private String buttonText;
	private Timer timer;

	public InstallServerMouseListener(AbstractButton abstractButton, ServerPanel serverPanel, TeejMediaServer mediaServer)
	{
		this.abstractButton = abstractButton;
		this.mediaServer = mediaServer;
		this.serverPanel = serverPanel;
		this.buttonText = abstractButton.getText();
		this.timer = new Timer(1000, (ActionEvent) -> mouseExited(null));
		mediaServer.addServerStateListener(this);
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		if (abstractButton.isEnabled() && !mediaServer.isInstalling())
		{
			SwingEDT.invokeOutside(() -> mediaServer.install());
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
		if(!abstractButton.isEnabled() && !mediaServer.isInstalling())
		{
			abstractButton.setEnabled(true);
			abstractButton.setText(REINSTALL);
			timer.start();
		}
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
		if(abstractButton.getText().equals(REINSTALL))
		{
			if(timer.isRunning())
			{
				timer.stop();
			}
			abstractButton.setEnabled(false);
			abstractButton.setText(buttonText);
		}
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
