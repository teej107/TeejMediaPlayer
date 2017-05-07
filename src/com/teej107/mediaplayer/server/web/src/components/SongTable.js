/**
 * Created by Tanner Norton on 5/4/2017.
 */
import React, {Component} from "react";
import axios from "axios";
import SongRow from "./SongRow";
import Clusterize from "clusterize.js";

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
            this.clusterize = new Clusterize({
                scrollId: 'song-div',
                contentId: 'song-tbody'
            });
        });
        //this.setState({library: Noxios});
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
                songRows.push(<SongRow song={obj} audioPlayer={this.audioPlayer} key={obj.path}/>);
            }
        }
        return (
            <div id="song-div">
                <table id="song-table">
                    <thead>
                    <tr>
                        <th>Title</th>
                        <th>Artist</th>
                        <th>Album</th>
                        <th>Length</th>
                    </tr>
                    </thead>
                    <tbody id="song-tbody" className="clusterize-content">
                    <tr className="clusterize-no-data">
                        <td>Loading...</td>
                    </tr>
                    { songRows }
                    </tbody>
                </table>
            </div>
        );
    }
}

export default SongTable;