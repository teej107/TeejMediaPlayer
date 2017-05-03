package com.teej107.mediaplayer.util;

import com.teej107.mediaplayer.Application;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;

/**
 * Created by teej107 on 4/20/17.
 */
public class SwingEDT
{
	public static boolean invoke(Runnable runnable)
	{
		if (SwingUtilities.isEventDispatchThread())
		{
			SwingUtilities.invokeLater(runnable);
		}
		else
		{
			try
			{
				SwingUtilities.invokeAndWait(runnable);
			}
			catch (InterruptedException | InvocationTargetException e)
			{
				e.printStackTrace();
				return false;
			}
		}
		return true;
	}

	public static void invokeOutside(Runnable runnable)
	{
		if(SwingUtilities.isEventDispatchThread())
		{
			Application.instance().getThreadService().submit(runnable);
		}
		else
		{
			runnable.run();
		}
	}
}
