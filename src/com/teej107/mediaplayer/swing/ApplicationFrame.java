package com.teej107.mediaplayer.swing;

import com.teej107.mediaplayer.Application;
import com.teej107.mediaplayer.io.WindowState;
import com.teej107.mediaplayer.util.ImageUtil;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.IOException;

/**
 * Created by teej107 on 4/14/2017.
 */
public class ApplicationFrame extends JFrame implements WindowListener, Runnable
{
	private Application application;
	private ApplicationPanel applicationPanel;

	public ApplicationFrame(Application application)
	{
		this.application = application;
		setTitle(application.getName());

		WindowState windowState = application.getApplicationPreferences().getWindowState();
		setSize(windowState.getWindowSize());
		setLocation(windowState.getLocation());
		setExtendedState(windowState.getWindowState());

		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(this);

		setJMenuBar(new ApplicationMenu());

		this.applicationPanel = new ApplicationPanel();
		setContentPane(applicationPanel);

		application.getMediaServer().addServerStateListener(applicationPanel.getStatusBar());
		application.getMediaServer().addInstallProgressListener(applicationPanel.getStatusBar());

		try
		{
			Image image = ImageUtil.ratioHeight(ImageIO.read(getClass().getResource("/assets/icon.png")), 128);
			setIconImage(image);
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		application.addShutdownHook(this, Integer.MAX_VALUE - 1);
	}

	public ApplicationStatusBar getApplicationStatusBar()
	{
		return applicationPanel.getStatusBar();
	}

	@Override
	public void windowOpened(WindowEvent e)
	{

	}

	@Override
	public void windowClosing(WindowEvent e)
	{
		application.exit();
	}

	@Override
	public void windowClosed(WindowEvent e)
	{

	}

	@Override
	public void windowIconified(WindowEvent e)
	{

	}

	@Override
	public void windowDeiconified(WindowEvent e)
	{

	}

	@Override
	public void windowActivated(WindowEvent e)
	{

	}

	@Override
	public void windowDeactivated(WindowEvent e)
	{

	}

	@Override
	public void run()
	{
		WindowState windowState = application.getApplicationPreferences().getWindowState();
		windowState.setLocation(getLocation());
		if (getExtendedState() == Frame.NORMAL)
		{
			windowState.setWindowSize(getSize());
		}
		windowState.setWindowState(getExtendedState());
	}
}
