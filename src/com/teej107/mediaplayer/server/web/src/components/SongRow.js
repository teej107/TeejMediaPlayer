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
    }

    render()
    {
        return (
            <tr>
                <td> { this.state.title } </td>
                <td> { this.state.artist } </td>
                <td> { this.state.album } </td>
                <td> {this.state.duration} </td>
            </tr>
        );
    }
}

export default SongRow;