/**
 * Created by Tanner Norton on 4/30/2017.
 */
class AudioPlayer
{
    constructor()
    {
        this.mediaPlayer = new Audio();
        this.songChangeListeners = [];
    }


    setSong(song)
    {
        if (!song)
            return false;
        this.song = song;
        this.mediaPlayer.src = song.path;
        this.songChangeListeners.forEach(function (callback)
        {
           callback(song);
        });
        return true;
    }

    play(song)
    {
        if (song !== undefined)
        {
            this.setSong(song);
        }
        this.mediaPlayer.play();
    }

    pause()
    {
        this.mediaPlayer.pause();
    }
}

export default AudioPlayer;