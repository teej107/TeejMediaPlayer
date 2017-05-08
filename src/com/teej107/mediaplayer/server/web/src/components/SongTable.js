/**
 * Created by Tanner Norton on 5/4/2017.
 */
import React, {Component} from "react";
import axios from "axios";
import SongRow from "./SongRow";
import test from "../Test";
import Clusterize from 'clusterize.js';

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
        var initCluster = () =>
        {
            this.clusterize = new Clusterize({
                scrollId: 'song-div',
                contentId: 'song-tbody',
                callbacks: {
                    clusterWillChange: function ()
                    {
                        console.log("cluster will change");
                    },
                    clusterChanged: function ()
                    {
                        console.log("cluster changed");
                    },
                    scrollingProgress: function (progress)
                    {
                        console.log(progress);
                    }
                }
            });
        };
        axios.get("/api/library").then((response) =>
        {
            this.setState({library: response.data});
            initCluster();
        }, (failure) =>
        {
            this.setState({library: test});
            initCluster();
        });
    }

    onContentIdClick(e)
    {
        var song = this.state.library[Object.keys(this.state.library)[e.target.parentElement.rowIndex - 1]];
        this.audioPlayer.play(song);
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
                songRows.push(<SongRow song={obj} key={obj.path}/>);
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
                    <tbody id="song-tbody" className="clusterize-content" onClick={this.onContentIdClick.bind(this)}>
                    { songRows }
                    </tbody>
                </table>
            </div>
        );
    }
}

export default SongTable;