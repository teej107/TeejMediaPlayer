package com.teej107.mediaplayer.swing.controls;

import com.teej107.mediaplayer.io.ImageLoader;
import com.teej107.mediaplayer.media.AudioPlayer;
import com.teej107.mediaplayer.media.SongPlaybackListener;
import com.teej107.mediaplayer.swing.components.ImageButton;
import com.teej107.mediaplayer.swing.listener.AudioPlaybackMouseListener;
import com.teej107.mediaplayer.util.ImageUtil;

import javax.swing.*;
import java.awt.*;

import static com.teej107.mediaplayer.util.ImageUtil.scaleToDimension;

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
		ImageLoader loader = ImageLoader.getInstance();

		String path = "/assets/button/";
		Image playImage = scaleToDimension(loader.getPlayButton(false), preferredDimension);
		this.playIcon = new ImageIcon(playImage);
		Image playPressImage = scaleToDimension(loader.getPlayButton(true), preferredDimension);
		this.playPressIcon = new ImageIcon(playPressImage);
		this.pauseIcon = new ImageIcon(scaleToDimension(loader.getPauseButton(false), preferredDimension));
		this.pausePressIcon = new ImageIcon(scaleToDimension(loader.getPauseButton(true), preferredDimension));

		Image nextImage = scaleToDimension(loader.getNextButton(false), preferredDimension);
		this.nextIcon = new ImageIcon(nextImage);
		Image nextPressImage = scaleToDimension(loader.getNextButton(true), preferredDimension);
		this.nextPressIcon = new ImageIcon(nextPressImage);
		this.prevIcon = new ImageIcon(ImageUtil.flip(nextImage, -1, 1));
		this.prevPressIcon = new ImageIcon(ImageUtil.flip(nextPressImage, -1, 1));

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
