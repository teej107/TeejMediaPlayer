package com.teej107.mediaplayer.media;

import com.teej107.mediaplayer.media.audio.ISong;
import com.teej107.mediaplayer.media.volume.VolumeChangeListener;
import com.teej107.mediaplayer.media.volume.VolumeManager;
import com.teej107.mediaplayer.util.SwingEDT;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Created by teej107 on 4/15/2017.
 */
public class AudioPlayer implements ChangeListener<Duration>, VolumeChangeListener, Runnable
{
	private VolumeManager volumeManager;
	private MediaPlayer mediaPlayer;
	private ISong currentSong;
	private Collection<TimeChangeListener> timeChangeListeners;
	private Collection<SongChangeListener> songChangeListeners;
	private Collection<EndOfMediaListener> endOfMediaListeners;
	private Collection<SongPlaybackListener> songPlaybackListeners;

	public AudioPlayer(VolumeManager volumeManager)
	{
		this.volumeManager = volumeManager;
		this.timeChangeListeners = new HashSet<>();
		this.songChangeListeners = new HashSet<>();
		this.endOfMediaListeners = new HashSet<>();
		this.songPlaybackListeners = new HashSet<>();
	}

	public void setSong(final ISong song)
	{
		stop();
		this.mediaPlayer = new MediaPlayer(new Media(song.getURI().toString()));
		this.mediaPlayer.setVolume(volumeManager.getVolume());
		this.mediaPlayer.currentTimeProperty().addListener(this);
		this.mediaPlayer.setOnEndOfMedia(this);
		volumeManager.addVolumeChangeListener(this);
		this.currentSong = song;

		SwingEDT.invoke(() ->
		{
			for (SongChangeListener listener : songChangeListeners)
			{
				listener.onSongChange(song);
			}
		});
	}

	public void seek(int seconds)
	{
		if(mediaPlayer != null)
		{
			mediaPlayer.seek(new Duration(TimeUnit.SECONDS.toMillis(seconds)));
		}
	}

	public VolumeManager getVolumeManager()
	{
		return volumeManager;
	}

	public ISong getSong()
	{
		return currentSong;
	}

	public boolean isPlaying()
	{
		return mediaPlayer != null && mediaPlayer.getStatus() == MediaPlayer.Status.PLAYING;
	}

	public void play()
	{
		if (mediaPlayer != null && !isPlaying())
		{
			mediaPlayer.play();
			for(SongPlaybackListener listener : songPlaybackListeners)
			{
				listener.onPlay();
			}
		}
	}

	public void pause()
	{
		if (mediaPlayer != null && isPlaying())
		{
			mediaPlayer.pause();
			for(SongPlaybackListener listener : songPlaybackListeners)
			{
				listener.onPause();
			}
		}
	}

	public void stop()
	{
		if (mediaPlayer != null)
		{
			mediaPlayer.dispose();
			mediaPlayer = null;
			currentSong = null;
			for(SongPlaybackListener listener : songPlaybackListeners)
			{
				listener.onStop();
			}
		}
	}

	public boolean addTimeChangeListener(TimeChangeListener listener)
	{
		return timeChangeListeners.add(listener);
	}

	public boolean addSongChangeListener(SongChangeListener listener)
	{
		return songChangeListeners.add(listener);
	}

	public boolean addEndOfMediaListener(EndOfMediaListener listener)
	{
		return endOfMediaListeners.add(listener);
	}

	public boolean addSongPlaybackListener(SongPlaybackListener listener)
	{
		return songPlaybackListeners.add(listener);
	}

	@Override
	public void changed(ObservableValue<? extends Duration> observable, Duration oldValue, Duration newValue)
	{
		final int seconds = (int) Math.ceil(oldValue.toSeconds());
		SwingEDT.invoke(() ->
		{
			for (TimeChangeListener listener : timeChangeListeners)
			{
				listener.onTimeChange(seconds);
			}
		});

	}

	@Override
	public void onVolumeChange(double volume)
	{
		if (mediaPlayer != null)
		{
			mediaPlayer.setVolume(volume);
		}
	}

	@Override
	public void run()
	{
		for(EndOfMediaListener listener : endOfMediaListeners)
		{
			listener.onEndOfMedia();
		}
	}
}
