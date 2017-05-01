package com.teej107.mediaplayer.swing.controls;

import com.teej107.mediaplayer.media.AudioPlayer;
import com.teej107.mediaplayer.media.SongPlaybackListener;
import com.teej107.mediaplayer.swing.components.ImageButton;
import com.teej107.mediaplayer.swing.listener.AudioPlaybackMouseListener;
import com.teej107.mediaplayer.util.ImageUtil;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

/**
 * Created by teej107 on 4/18/17.
 */
public class PlaybackControlPanel extends JPanel implements SongPlaybackListener
{
	private JButton previous, next;
	private ImageButton playToggle;
	private ImageIcon playIcon, playPressIcon, pauseIcon, pausePressIcon, nextIcon, nextPressIcon, prevIcon, prevPressIcon;

	public PlaybackControlPanel(AudioPlayer audioPlayer)
	{
		super(new GridLayout(1, 3));
		Dimension preferredDimension = new Dimension(0, 100);
		try
		{
			String path = "/assets/button/";
			Image playImage = ImageUtil.loadImageResource(path + "play.png", preferredDimension);
			this.playIcon = new ImageIcon(playImage);
			Image playPressImage = ImageUtil.loadImageResource(path + "play press.png", preferredDimension);
			this.playPressIcon = new ImageIcon(playPressImage);
			this.pauseIcon = new ImageIcon(ImageUtil.loadImageResource(path + "pause.png", preferredDimension));
			this.pausePressIcon = new ImageIcon(ImageUtil.loadImageResource(path + "pause press.png", preferredDimension));

			Image nextImage = ImageUtil.loadImageResource(path + "next.png", preferredDimension);
			this.nextIcon = new ImageIcon(nextImage);
			Image nextPressImage = ImageUtil.loadImageResource(path + "next press.png", preferredDimension);
			this.nextPressIcon = new ImageIcon(nextPressImage);
			this.prevIcon = new ImageIcon(ImageUtil.flip(nextImage, -1, 1));
			this.prevPressIcon = new ImageIcon(ImageUtil.flip(nextPressImage, -1, 1));
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}

		this.previous = new ImageButton(prevIcon, prevPressIcon, preferredDimension);
		this.playToggle = new ImageButton(playIcon, playPressIcon, preferredDimension);
		this.playToggle.addMouseListener(new AudioPlaybackMouseListener(audioPlayer));
		this.next = new ImageButton(nextIcon, nextPressIcon, preferredDimension);

		add(previous);
		add(playToggle);
		add(next);

		audioPlayer.addSongPlaybackListener(this);
	}

	@Override
	public void onPause()
	{
		playToggle.setIcon(playIcon);
		playToggle.setPressedIcon(playPressIcon);
	}

	@Override
	public void onPlay()
	{
		playToggle.setIcon(pauseIcon);
		playToggle.setPressedIcon(pausePressIcon);
	}

	@Override
	public void onStop()
	{
		onPause();
	}
}
