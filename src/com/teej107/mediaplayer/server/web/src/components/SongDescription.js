/**
 * Created by Tanner Norton on 4/30/2017.
 */
import React, {Component} from "react";
import AudioPlayer from '../media/AudioPlayer';

class SongDescription extends Component
{
    constructor(props)
    {
        super(props);
        props.audioPlayer.songChangeListeners.push((song) =>
        {
            this.setState({
                title: song.title,
                artist: song.artist,
                album: song.album
            })
        });
        this.state = AudioPlayer.noSong();
    }

    render()
    {
        return (
            <div id="song-description">
                <p>{ this.state.title }</p>
                <p>{ this.state.artist }</p>
                <p>{ this.state.album }</p>
            </div>
        );
    }
}

export default SongDescription