/**
 * Created by Tanner Norton on 5/4/2017.
 */
import React, {Component} from "react";
import AudioPlayer from '../media/AudioPlayer';

class SongRow extends Component
{
    constructor(props)
    {
        super(props);
        this.song = props.song;
        this.index = props.index;
    }

    render()
    {
        return (
            <div className="row song-row">
                <index>{this.index}</index>
                <div>
                    <p> { this.song.title } </p>
                    <p> { this.song.artist } </p>
                </div>
                <div>
                    <p> { this.song.album } </p>
                    <p> { AudioPlayer.formatDuration(this.song.duration) } </p>
                </div>
            </div>
        );
    }
}

export default SongRow;