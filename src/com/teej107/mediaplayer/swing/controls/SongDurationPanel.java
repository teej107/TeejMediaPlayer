package com.teej107.mediaplayer.swing.controls;

import com.teej107.mediaplayer.media.*;
import com.teej107.mediaplayer.media.audio.ISong;
import com.teej107.mediaplayer.util.SwingEDT;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Created by teej107 on 4/20/17.
 */
public class SongDurationPanel extends JPanel implements TimeChangeListener, SongChangeListener, ChangeListener, MouseListener
{
	private AudioPlayer audioPlayer;
	private JLabel currentTime, remainingTime;
	private JSlider durationSlider;
	private boolean dragging, draggingDone;

	public SongDurationPanel(AudioPlayer audioPlayer)
	{
		super(new BorderLayout());
		this.audioPlayer = audioPlayer;
		this.currentTime = new JLabel("0:00");
		this.remainingTime = new JLabel("0:00");
		this.durationSlider = new JSlider(0, 0);
		this.durationSlider.addChangeListener(this);
		this.durationSlider.addMouseListener(this);
		this.dragging = false;
		this.draggingDone = false;

		durationSlider.setPaintLabels(true);
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
		if(!dragging)
		{
			SwingEDT.invoke(() -> durationSlider.setValue(currentSeconds));
		}

		//This must be after the above statement and is used to prevent a split second durationSlider snap back
		if(draggingDone)
		{
			dragging = false;
			draggingDone = false;
		}
	}

	@Override
	public void onSongChange(ISong song)
	{
		SwingEDT.invoke(() ->
		{
			durationSlider.setValue(0);
			durationSlider.setMaximum((int) song.getDuration());
		});

	}

	@Override
	public void stateChanged(ChangeEvent e)
	{
		if(durationSlider.getValueIsAdjusting())
		{
			dragging = true;
		}
		int currentSeconds = durationSlider.getValue();
		int timeLeft = durationSlider.getMaximum() - currentSeconds;
		if (timeLeft > -1)
		{
			currentTime.setText(formatNumber(currentSeconds / 60, currentSeconds % 60));
			remainingTime.setText(formatNumber(timeLeft / 60, timeLeft % 60));
		}
	}

	@Override
	public void mouseClicked(MouseEvent e)
	{

	}

	@Override
	public void mousePressed(MouseEvent e)
	{

	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		if(dragging)
		{
			audioPlayer.seek(durationSlider.getValue());
			draggingDone = true;
		}
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
