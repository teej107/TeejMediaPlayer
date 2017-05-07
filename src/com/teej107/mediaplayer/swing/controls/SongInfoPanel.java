package com.teej107.mediaplayer.swing.controls;

import com.teej107.mediaplayer.media.AudioPlayer;
import com.teej107.mediaplayer.media.SongChangeListener;
import com.teej107.mediaplayer.media.audio.ISong;
import com.teej107.mediaplayer.util.SwingEDT;

import javax.swing.*;

/**
 * Created by teej107 on 4/20/17.
 */
public class SongInfoPanel extends JPanel implements SongChangeListener
{
	private AudioPlayer audioPlayer;
	private JLabel title, artist, album;

	public SongInfoPanel(AudioPlayer audioPlayer)
	{
		this.audioPlayer = audioPlayer;
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		this.title = new JLabel("---", JLabel.CENTER);
		this.artist = new JLabel("---", JLabel.CENTER);
		this.album = new JLabel("---", JLabel.CENTER);

		add(title);
		add(artist);
		add(album);

		audioPlayer.addSongChangeListener(this);
	}

	@Override
	public void onSongChange(ISong song)
	{
		SwingEDT.invoke(() ->
		{
			title.setText(song.getTitle());
			artist.setText(song.getArtist());
			album.setText(song.getAlbum());
		});

	}
}
