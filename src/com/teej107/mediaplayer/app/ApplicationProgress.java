package com.teej107.mediaplayer.app;

import com.teej107.mediaplayer.server.ServerStateListener;
import com.teej107.mediaplayer.util.ProgressListener;

import java.util.*;

/**
 * Created by teej107 on 6/14/2017.
 */
public class ApplicationProgress implements ServerStateListener, ProgressListener
{
	private int min, value, max;
	private String progressStatus;
	private Collection<ApplicationProgressListener> statusListeners;

	public ApplicationProgress()
	{
		this.statusListeners = new HashSet<>();
	}

	public boolean addApplicationStatusListener(ApplicationProgressListener listener)
	{
		return statusListeners.add(listener);
	}

	public int getMin()
	{
		return min;
	}

	public void setMin(int min)
	{
		if(this.min == min)
			return;

		this.min = min;
		for(ApplicationProgressListener listener : statusListeners)
		{
			listener.onMinimumChange(min);
		}
	}

	public int getValue()
	{
		return value;
	}

	public void setValue(int value)
	{
		if(this.value == value)
			return;
		this.value = value;
		for(ApplicationProgressListener listener : statusListeners)
		{
			listener.onValueChange(value);
		}
	}

	public int getMax()
	{
		return max;
	}

	public void setMax(int max)
	{
		if(this.max == max)
			return;

		this.max = max;
		for(ApplicationProgressListener listener : statusListeners)
		{
			listener.onMaximumChange(max);
		}
	}

	public String getStatus()
	{
		return progressStatus;
	}

	public void setStatus(String progressStatus)
	{
		if(Objects.equals(this.progressStatus, progressStatus))
			return;

		this.progressStatus = progressStatus;
		for(ApplicationProgressListener listener : statusListeners)
		{
			listener.onStatusChange(progressStatus);
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
		setStatus("Installing Server...");
	}

	@Override
	public void onInstalled()
	{
		setStatus(null);
	}

	@Override
	public void onProgressChange(int min, int value, int max)
	{
		setMax(max);
		setValue(value);
	}
}
