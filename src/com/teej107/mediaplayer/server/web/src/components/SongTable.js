/**
 * Created by Tanner Norton on 5/4/2017.
 */
import React, {Component} from "react";
import axios from "axios";
import SongRow from "./SongRow";

class SongTable extends Component
{
    constructor(props)
    {
        super(props);
        this.state = {
            library: []
        };
        this.audioPlayer = props.audioPlayer;

    }

    componentWillMount()
    {
        axios.get("/api/library").then((response) =>
        {
            this.setState({library: response.data});
        });
    }

    render()
    {
        const library = this.state.library;
        const songRows = [];
        for (var key in library)
        {
            if (library.hasOwnProperty(key))
            {
                var obj = library[key];
                obj.path = "/api/media/" + obj.path;
                songRows.push(<SongRow song={obj} audioPlayer={this.audioPlayer}/>);
            }
        }
        return (
            <div className="song-div">
                <table className="song-table">
                    <tr>
                        <th>Title</th>
                        <th>Artist</th>
                        <th>Album</th>
                        <th>Length</th>
                    </tr>
                    { songRows }
                </table>
            </div>
        );
    }
}

export default SongTable;