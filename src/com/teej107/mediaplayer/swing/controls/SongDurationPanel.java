package com.teej107.mediaplayer.swing.controls;

import com.teej107.mediaplayer.media.*;
import com.teej107.mediaplayer.media.audio.ISong;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;

/**
 * Created by teej107 on 4/20/17.
 */
public class SongDurationPanel extends JPanel implements TimeChangeListener, SongChangeListener, ChangeListener
{
	private AudioPlayer audioPlayer;
	private JLabel currentTime, remainingTime;
	private JSlider durationSlider;

	public SongDurationPanel(AudioPlayer audioPlayer)
	{
		super(new BorderLayout());
		this.audioPlayer = audioPlayer;
		this.currentTime = new JLabel("0:00");
		this.remainingTime = new JLabel("0:00");
		this.durationSlider = new JSlider(0, 0);
		this.durationSlider.addChangeListener(this);

		add(currentTime, BorderLayout.LINE_START);
		add(durationSlider, BorderLayout.CENTER);
		add(remainingTime, BorderLayout.LINE_END);

		audioPlayer.addSongChangeListener(this);
		audioPlayer.addTimeChangeListener(this);
	}

	private static String formatNumber(int minutes, int seconds)
	{
		return (minutes < 10 ? "0" + minutes : minutes) + ":" + (seconds < 10 ? "0" + seconds : seconds);
	}

	@Override
	public void onTimeChange(int currentSeconds)
	{
		durationSlider.setValue(currentSeconds);
		int timeLeft = durationSlider.getMaximum() - currentSeconds;
		if(timeLeft > -1)
		{
			currentTime.setText(formatNumber(currentSeconds / 60, currentSeconds % 60));
			remainingTime.setText(formatNumber(timeLeft / 60, timeLeft % 60));
		}
	}

	@Override
	public void onSongChange(ISong song)
	{
		durationSlider.setValue(0);
		durationSlider.setMaximum((int) song.getDuration());
	}

	@Override
	public void stateChanged(ChangeEvent e)
	{
		if(durationSlider.getValueIsAdjusting())
		{
			audioPlayer.seek(durationSlider.getValue());
		}
	}
}
