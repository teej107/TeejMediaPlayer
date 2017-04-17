package com.teej107.mediaplayer.swing.component;

import com.teej107.mediaplayer.media.volume.VolumeManager;

import javax.swing.*;

/**
 * Created by teej107 on 4/16/2017.
 */
public class MusicInfoControlPanel extends JPanel
{
	private VolumeControlPanel volumeControlPanel;
	private AlbumCoverPanel albumCoverPanel;

	public MusicInfoControlPanel()
	{
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		add(new VolumeControlPanel(new VolumeManager(1)));
	}
}
