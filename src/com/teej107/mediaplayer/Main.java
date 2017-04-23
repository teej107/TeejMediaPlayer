package com.teej107.mediaplayer;

import com.sun.javafx.application.PlatformImpl;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by teej107 on 4/14/2017.
 */
public class Main
{
	public static void main(String[] args)
	{
		List<String> arguments = toArgumentList(args);
		Application app = Application.instance().init();
		if(arguments.contains("-server"))
		{
			app.getMediaServer().start();
			System.out.println("Type 'exit' to stop");
			try(BufferedReader br = new BufferedReader(new InputStreamReader(System.in)))
			{
				while(app.getMediaServer().isRunning())
				{
					List<String> line = toArgumentList(br.readLine().split(" "));
					if(line.contains("exit"))
					{
						app.getMediaServer().stop();
					}
					if(line.contains("-gui"))
					{
						showGUI(app);
					}
				}
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
		else
		{
			showGUI(app);
		}

	}

	private static void showGUI(Application app)
	{
		if(!setLookAndFeel("com.alee.laf.WebLookAndFeel"))
		{
			setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		}
		PlatformImpl.startup(() -> {});
		app.createGui();
	}

	private static List<String> toArgumentList(String[] args)
	{
		List<String> argsList = new ArrayList<>();
		for(String s : args)
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
