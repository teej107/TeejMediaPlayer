package com.teej107.mediaplayer;

import com.teej107.mediaplayer.io.ApplicationPreferences;
import com.teej107.mediaplayer.swing.ApplicationFrame;
import com.teej107.mediaplayer.util.ComparableObject;

import java.util.*;

/**
 * A singleton style class used for managing application wide objects
 */
public class Application implements Comparator<ComparableObject<Runnable>>
{
	private SortedSet<ComparableObject<Runnable>> shutdownHooks;
	private ApplicationPreferences applicationPreferences;
	private ApplicationFrame applicationFrame;

	protected Application()
	{
		this.shutdownHooks = new TreeSet<>(this);

		this.applicationPreferences = new ApplicationPreferences(this);
		this.applicationFrame = new ApplicationFrame(this);
		this.applicationFrame.setVisible(true);
	}

	/**
	 * Get the application name
	 * @return application name
	 */
	public String getName()
	{
		return "Teej Media Player";
	}

	public ApplicationPreferences getApplicationPreferences()
	{
		return applicationPreferences;
	}

	/**
	 * Add a shutdown hook to run code when the application exits
	 * @param runnable Runnable object
	 * @param priority priority of when the Runnable run method should be invoked. Lower integers run first. Higher integers run last.
	 */
	public boolean addShutdownHook(Runnable runnable, int priority)
	{
		return shutdownHooks.add(new ComparableObject<>(runnable, priority));
	}

	/**
	 * Invokes the shutdown hooks then quits the application
	 */
	public void exit()
	{
		for(ComparableObject<Runnable> obj : shutdownHooks)
		{
			obj.getObject().run();
		}
		System.exit(0);
	}

	/**
	 * Used for sorting shutdown hooks from the interface Comparator
	 */
	@Override
	public int compare(ComparableObject<Runnable> t1, ComparableObject<Runnable> t2)
	{
		return t1.getPriority() - t2.getPriority();
	}
}
