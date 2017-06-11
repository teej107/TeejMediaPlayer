/**
 * Created by Tanner Norton on 4/30/2017.
 */
import Playlist from "./Playlist";

class AudioPlayer
{
    constructor()
    {
        this.mediaPlayer = new Audio();
        this.playlist = new Playlist();
        this.songChangeListeners = [];
        this.songPlaybackListeners = [];
        this.songEndListeners = [];
        this.songErrorListeners = [];
        this.songTimeUpdateListeners = [];
        this.mediaPlayer.addEventListener('timeupdate', () =>
        {
            this.songTimeUpdateListeners.forEach((callback) => callback(this.mediaPlayer.currentTime));
        });
        this.mediaPlayer.addEventListener('ended', () =>
        {
            this.next(true);
            this.songEndListeners.forEach((callback) => callback());
        });
        this.mediaPlayer.addEventListener('error', () =>
        {
            const noSong = AudioPlayer.noSong();
            this.songChangeListeners.forEach((callback) => callback(noSong));
            this.songErrorListeners.forEach((callback) => callback());
            alert("Source not found :(");
        });
    }

    toggle = () =>
    {
        if (this.isPlaying())
        {
            this.pause();
        }
        else
        {
            this.play();
        }
    };

    isPlaying = () =>
    {
        return !this.mediaPlayer.paused && !this.mediaPlayer.ended && !isNaN(this.mediaPlayer.duration);
    };

    setSong = (song) =>
    {
        var noSong = AudioPlayer.noSong();
        this.songChangeListeners.forEach((callback) =>
        {
            callback(song ? song : noSong);
        });
        this.song = song;
        if (!song)
        {
            this.mediaPlayer.pause();
            return false;
        }
        this.mediaPlayer.src = '/api/media/' + song.path;
        //TODO: Debug file path sometimes has space at end. Causes problems on Windows, Alice Cooper - Public Animal #9
        return true;
    };

    play = (song) =>
    {
        if (song)
        {
            this.setSong(song);
        }
        if (!this.song)
            return;

        this.mediaPlayer.play();
        this.songPlaybackListeners.forEach((callback) => callback(true));
    };

    pause = () =>
    {
        if (!this.song)
            return;

        this.mediaPlayer.pause();
        this.songPlaybackListeners.forEach((callback) => callback(false));
    };

    next = (play, userInput) =>
    {
        play = play || this.isPlaying();
        var playbackMode = this.playlist.playbackMode();
        var repeatList = this.playlist.PlaybackModeEnum.REPEAT_LIST;
        var normal = this.playlist.PlaybackModeEnum.NORMAL;
        var playlistFn = () =>
        {
            return userInput ? this.playlist.next(playbackMode === repeatList ? repeatList : normal) : this.playlist.next();
        };
        var song = playlistFn();
        this.setSong(song);
        if(play)
        {
            this.play();
        }
    };

    previous = (play) =>
    {
        play = play || this.isPlaying();
        var song = this.playlist.previous();
        this.setSong(song);
        if(play)
        {
            this.play();
        }
    };

    static formatDuration(duration)
    {
        if (isNaN(duration) || duration < 0)
        {
            duration = 0;
        }
        var date = new Date(null);
        date.setSeconds(duration);
        return date.toISOString().substr(14, 5);
    }

    static noSong()
    {
        return {artist: '---', album: '---', title: '---', duration: 0};
    }
}

export default AudioPlayer;
