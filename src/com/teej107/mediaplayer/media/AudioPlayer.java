package com.teej107.mediaplayer.media;

import com.teej107.mediaplayer.media.audio.ISong;
import com.teej107.mediaplayer.media.volume.VolumeManager;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.net.URI;

/**
 * Created by teej107 on 4/15/2017.
 */
public class AudioPlayer
{
	private VolumeManager volumeManager;
	private MediaPlayer mediaPlayer;
	private ISong currentSong;

	public AudioPlayer(VolumeManager volumeManager)
	{
		this.volumeManager = volumeManager;
	}

	public void setSong(ISong song)
	{
		stop();
		this.mediaPlayer = new MediaPlayer(new Media(song.getURI().toString()));
		this.mediaPlayer.setVolume(volumeManager.getVolume());
		this.currentSong = song;
	}

	public boolean isPlaying()
	{
		return currentSong != null && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING;
	}

	public boolean play()
	{
		return false;
	}

	public void pause()
	{

	}

	public void stop()
	{
		if (mediaPlayer != null)
		{
			mediaPlayer.dispose();
			mediaPlayer = null;
			currentSong = null;
		}
	}
}
