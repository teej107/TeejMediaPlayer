package com.teej107.mediaplayer.io;

import com.teej107.mediaplayer.Application;
import com.teej107.mediaplayer.media.volume.VolumeManager;
import org.json.simple.parser.JSONParser;

import java.util.Map;

/**
 * Created by teej107 on 4/20/17.
 */
public class PlayerState extends AbstractJsonState
{
	private static final String VOLUME = "volume";

	protected PlayerState(String rawData)
	{
		super(rawData);
	}

	public void setVolume(double volume)
	{
		dataMap.put(VOLUME, volume);
	}

	public double getVolume()
	{
		Double dble = (Double) dataMap.get(VOLUME);
		if (dble == null)
			return VolumeManager.getSystemVolumeLevel();
		
		return dble.doubleValue();
	}
}
