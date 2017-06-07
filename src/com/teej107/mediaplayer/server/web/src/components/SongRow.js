/**
 * Created by Tanner Norton on 5/4/2017.
 */
import React, {Component} from "react";

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
                    <p> { SongRow.formatDuration(this.song.duration) } </p>
                </div>
            </div>
        );
    }

    static formatDuration(duration)
    {
        var date = new Date(null);
        date.setSeconds(duration);
        return date.toISOString().substr(14, 5);
    }
}

export default SongRow;