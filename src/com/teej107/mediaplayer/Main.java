package com.teej107.mediaplayer;

import com.sun.javafx.application.PlatformImpl;

import javax.swing.*;
import java.util.*;

/**
 * Created by teej107 on 4/14/2017.
 */
public class Main
{
	public static void main(String[] args)
	{
		List<String> arguments = toArgumentList(args);
		if(!setLookAndFeel("com.alee.laf.WebLookAndFeel"))
		{
			setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		PlatformImpl.startup(() -> {});
		Application app = Application.instance().init();
		if(!arguments.contains("-server"))
		{
			app.createGui();
		}
	}

	private static List<String> toArgumentList(String[] args)
	{
		List<String> argsList = new ArrayList<>();
		for(String s : argsList)
		{
			argsList.add(s.toLowerCase());
		}
		return argsList;
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
