package com.teej107.mediaplayer.swing;

import com.teej107.mediaplayer.Application;
import com.teej107.mediaplayer.io.WindowState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

/**
 * Created by teej107 on 4/14/2017.
 */
public class ApplicationFrame extends JFrame implements WindowListener, Runnable
{
	private Application application;

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

		setContentPane(new ApplicationPanel());

		application.addShutdownHook(this, Integer.MAX_VALUE - 1);
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
		if(getExtendedState() == Frame.NORMAL)
		{
			windowState.setWindowSize(getSize());
		}
		windowState.setWindowState(getExtendedState());
	}
}
