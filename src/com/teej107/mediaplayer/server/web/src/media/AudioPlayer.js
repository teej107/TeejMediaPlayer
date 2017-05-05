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
        console.log(url);
        if(!url)
            return false;
        this.mediaPlayer.src = url;
        return true;
    }

    play(url)
    {
        if(url !== undefined)
        {
            this.setSong(url);
        }
        this.mediaPlayer.play();
    }

    pause()
    {
        this.mediaPlayer.pause();
    }
}

export default AudioPlayer;