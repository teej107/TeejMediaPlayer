package com.teej107.mediaplayer;

import com.sun.javafx.application.PlatformImpl;

import javax.swing.*;

/**
 * Created by teej107 on 4/14/2017.
 */
public class Main
{
	public static void main(String[] args)
	{
		setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		PlatformImpl.startup(() -> {});
		SwingUtilities.invokeLater(() -> Application.instance().init());
	}

	private static boolean setLookAndFeel(String s)
	{
		try
		{
			UIManager.setLookAndFeel(s);
			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			return false;
		}
	}
}
