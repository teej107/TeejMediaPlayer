/**
 * Created by Tanner Norton on 4/30/2017.
 */
class AudioPlayer
{
    constructor()
    {
        this.mediaPlayer = new Audio();
        this.mediaPlayer.addEventListener('ended', function ()
        {

        }.bind(this));
        this.songChangeListeners = [];
    }

    setSong(song)
    {
        if (!song)
            return false;
        this.song = song;
        this.mediaPlayer.src = '/api/media/' + song.path;
        //TODO: Debug file path sometimes has space at end. Causes problems on Windows, Alice Cooper - Public Animal #9
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