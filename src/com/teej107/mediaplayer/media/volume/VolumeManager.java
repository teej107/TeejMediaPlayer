package com.teej107.mediaplayer.media.volume;

import javax.sound.sampled.*;
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
		this.volume = Math.max(Math.min(volume, 1), 0);
		this.volumeChangeListeners = new HashSet<>();
	}

	public double getVolume()
	{
		return volume;
	}

	public void setVolume(double volume)
	{
		this.volume = volume;
		for (VolumeChangeListener listener : volumeChangeListeners)
		{
			listener.onVolumeChange(volume);
		}
	}

	public boolean addVolumeChangeListener(VolumeChangeListener listener)
	{
		return volumeChangeListeners.add(listener);
	}

	public static double getSystemVolumeLevel()
	{
		Mixer.Info[] mixerInfos = AudioSystem.getMixerInfo();
		for (Mixer.Info mixerInfo : mixerInfos)
		{
			Mixer mixer = AudioSystem.getMixer(mixerInfo);
			Line.Info[] lineInfos = mixer.getTargetLineInfo();
			for (Line.Info lineInfo : lineInfos)
			{
				try
				{
					Line line = mixer.getLine(lineInfo);
					line.open();
					if (line.isControlSupported(FloatControl.Type.VOLUME))
					{
						FloatControl control = (FloatControl) line.getControl(FloatControl.Type.VOLUME);
						double vol = control.getValue();
						line.close();
						return vol;
					}
				}
				catch (LineUnavailableException e)
				{
					e.printStackTrace();
				}
			}
		}

		return 0.5;
	}
}
