package com.teej107.mediaplayer.swing.listener;

import com.teej107.mediaplayer.media.AudioPlayer;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by teej107 on 4/29/2017.
 */
public class AudioPlaybackMouseListener implements MouseListener
{
	private AudioPlayer audioPlayer;

	public AudioPlaybackMouseListener(AudioPlayer audioPlayer)
	{
		this.audioPlayer = audioPlayer;
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{
		if(audioPlayer.isPlaying())
		{
			audioPlayer.pause();
		}
		else
		{
			audioPlayer.play();
		}
	}

	@Override
	public void mousePressed(MouseEvent e)
	{

	}

	@Override
	public void mouseReleased(MouseEvent e)
	{

	}

	@Override
	public void mouseEntered(MouseEvent e)
	{

	}

	@Override
	public void mouseExited(MouseEvent e)
	{

	}
}
