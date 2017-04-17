package com.teej107.mediaplayer.media.volume;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by teej107 on 4/15/17.
 */
public class VolumeManager
{
	private double volume;
	private Collection<VolumeChangeListener> volumeChangeListeners;

	public VolumeManager(double volume)
	{
		this.volume = volume;
		this.volumeChangeListeners = new HashSet<>();
	}

	public double getVolume()
	{
		return volume;
	}

	public void setVolume(double volume)
	{
		this.volume = volume;
		for(VolumeChangeListener listener : volumeChangeListeners)
		{
			listener.onVolumeChange(volume);
		}
	}

	public boolean addVolumeChangeListener(VolumeChangeListener listener)
	{
		return volumeChangeListeners.add(listener);
	}
}
