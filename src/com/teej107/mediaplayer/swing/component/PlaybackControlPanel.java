package com.teej107.mediaplayer.swing.component;

import com.teej107.mediaplayer.media.AudioPlayer;

import javax.swing.*;
import java.awt.*;

/**
 * Created by teej107 on 4/18/17.
 */
public class PlaybackControlPanel extends JPanel
{
	private AudioPlayer audioPlayer;
	private JButton previous, playToggle, next;

	public PlaybackControlPanel(AudioPlayer audioPlayer)
	{
		super(new GridLayout(1, 3));
		this.audioPlayer = audioPlayer;
		this.previous = new JButton("<");
		this.playToggle = new JButton("[>");
		this.next = new JButton(">");

		add(previous);
		add(playToggle);
		add(next);
	}
}
