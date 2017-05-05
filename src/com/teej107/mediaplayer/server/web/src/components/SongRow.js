/**
 * Created by Tanner Norton on 5/4/2017.
 */
import React, {Component} from "react";

class SongRow extends Component
{
    constructor(props)
    {
        super(props);
        this.state = props.song;
        this.audioPlayer = props.audioPlayer;
    }

    render()
    {
        return (
            <tr onClick={ this.audioPlayer.play.bind(this.audioPlayer, this.state.path) }>
                <td> { this.state.title } </td>
                <td> { this.state.artist } </td>
                <td> { this.state.album } </td>
                <td> {this.state.duration} </td>
            </tr>
        );
    }
}

export default SongRow;