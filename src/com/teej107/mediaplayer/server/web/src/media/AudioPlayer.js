/**
 * Created by Tanner Norton on 4/30/2017.
 */
class AudioPlayer
{
    constructor()
    {
        this.mediaPlayer = new Audio();
    }

    setSong(url)
    {
        this.mediaPlayer.src = url;
    }

    play()
    {
        this.mediaPlayer.play();
    }

    pause()
    {
        this.mediaPlayer.pause();
    }
}

export default AudioPlayer;