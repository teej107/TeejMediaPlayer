package com.teej107.mediaplayer.swing.component;

import com.teej107.mediaplayer.Application;
import com.teej107.mediaplayer.media.AudioPlayer;
import com.teej107.mediaplayer.media.volume.VolumeManager;

import javax.swing.*;
import java.awt.*;

/**
 * Created by teej107 on 4/16/2017.
 */
public class MusicInfoControlPanel extends JPanel
{
	private JPanel controlsPanel, infoPanel;
	private VolumeControlPanel volumeControlPanel;
	private AlbumCoverPanel albumCoverPanel;
	private PlaybackControlPanel playbackControlPanel;
	private SongDurationPanel songDurationPanel;
	private SongInfoPanel songInfoPanel;

	public MusicInfoControlPanel(AudioPlayer audioPlayer)
	{
		super(new BorderLayout());
		this.controlsPanel = new JPanel(new BorderLayout());
		this.infoPanel = new JPanel(new BorderLayout());
		this.playbackControlPanel = new PlaybackControlPanel(audioPlayer);
		this.albumCoverPanel = new AlbumCoverPanel();
		this.volumeControlPanel = new VolumeControlPanel(audioPlayer.getVolumeManager());
		this.songInfoPanel = new SongInfoPanel(audioPlayer);
		this.songDurationPanel = new SongDurationPanel(audioPlayer);

		this.infoPanel.add(songInfoPanel, BorderLayout.PAGE_START);
		this.infoPanel.add(songDurationPanel, BorderLayout.PAGE_END);
		add(infoPanel, BorderLayout.PAGE_START);
		add(albumCoverPanel, BorderLayout.CENTER);
		this.controlsPanel.add(playbackControlPanel, BorderLayout.CENTER);
		this.controlsPanel.add(volumeControlPanel, BorderLayout.PAGE_END);
		add(controlsPanel, BorderLayout.PAGE_END);
	}
}
