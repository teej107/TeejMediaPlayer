package com.teej107.mediaplayer.swing.component;

import com.teej107.mediaplayer.media.volume.VolumeManager;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

/**
 * Created by teej107 on 4/16/2017.
 */
public class VolumeControlPanel extends JPanel implements ChangeListener
{
	private JSlider volumeSlider;
	private VolumeManager volumeManager;

	public VolumeControlPanel(VolumeManager volumeManager)
	{
		super(new BorderLayout());
		this.volumeManager = volumeManager;
		this.volumeSlider = new JSlider(0, 100);
		this.volumeSlider.setValue((int) volumeManager.getVolume());
		this.volumeSlider.addChangeListener(this);
		add(volumeSlider, BorderLayout.CENTER);
	}

	@Override
	public void stateChanged(ChangeEvent e)
	{
		volumeManager.setVolume(volumeSlider.getValue() / volumeSlider.getMaximum());
	}
}
