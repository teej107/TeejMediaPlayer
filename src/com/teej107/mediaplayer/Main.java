package com.teej107.mediaplayer;

import com.sun.javafx.application.PlatformImpl;

import javax.swing.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Created by teej107 on 4/14/2017.
 */
public class Main
{
	public static void main(String[] args)
	{
		List<String> arguments = toArgumentList(args);
		Application app = Application.instance().init();
		if (arguments.contains("-install-server") || arguments.contains("-is"))
		{
			System.out.println("Installing server...");
			AtomicInteger lastNum = new AtomicInteger();
			app.getMediaServer().addInstallProgressListener((min, value, max) ->
			{
				int percent = (int)((double) value / max * 100);
				if(percent != lastNum.get())
				{
					lastNum.set(percent);
					if(percent % 5 == 0)
					{
						System.out.print("█");
					}
				}
			});
			System.out.println("████████████████████ [progress]");
			app.getMediaServer().install();
			System.out.println();
			System.out.println("Server installed");
			System.out.println();
		}
		if (arguments.contains("-server"))
		{
			app.getMediaServer().start();
			System.out.println("Type 'exit' to stop");
			try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in)))
			{
				while (app.getMediaServer().isRunning())
				{
					List<String> line = toArgumentList(br.readLine().split(" "));
					if (line.contains("exit"))
					{
						app.getMediaServer().stop();
					}
					if (line.contains("-gui"))
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
		setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		PlatformImpl.startup(() ->
		{
		});
		app.createGui();
	}

	private static List<String> toArgumentList(String[] args)
	{
		List<String> argsList = new ArrayList<>();
		for (String s : args)
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
