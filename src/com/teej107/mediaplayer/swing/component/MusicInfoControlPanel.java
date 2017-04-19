package com.teej107.mediaplayer.swing.component;

import com.teej107.mediaplayer.media.volume.VolumeManager;

import javax.swing.*;
import java.awt.*;

/**
 * Created by teej107 on 4/16/2017.
 */
public class MusicInfoControlPanel extends JPanel
{
	private JPanel controlsPanel;
	private VolumeControlPanel volumeControlPanel;
	private AlbumCoverPanel albumCoverPanel;
	private PlaybackControlPanel playbackControlPanel;

	public MusicInfoControlPanel()
	{
		super(new BorderLayout());
		this.controlsPanel = new JPanel(new BorderLayout());
		this.playbackControlPanel = new PlaybackControlPanel(null);
		this.albumCoverPanel = new AlbumCoverPanel();
		this.volumeControlPanel = new VolumeControlPanel(new VolumeManager(1));


		add(albumCoverPanel, BorderLayout.CENTER);
		this.controlsPanel.add(playbackControlPanel, BorderLayout.CENTER);
		this.controlsPanel.add(volumeControlPanel, BorderLayout.PAGE_END);
		add(controlsPanel, BorderLayout.PAGE_END);
	}
}
