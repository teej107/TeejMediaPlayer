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
		try
		{
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		PlatformImpl.startup(() -> {});
		SwingUtilities.invokeLater(() -> Application.instance().init());
	}
}
