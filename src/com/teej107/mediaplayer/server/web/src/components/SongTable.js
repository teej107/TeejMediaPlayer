/**
 * Created by Tanner Norton on 5/4/2017.
 */
import React, {Component} from "react";
import Axios from "axios";
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
                scrollId: 'song-list',
                contentId: 'song-list'
            });
        };
        Axios.get("/api/library").then((response) =>
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
        var indexElement = e.target.parentElement.getElementsByClassName("index")[0];
        this.audioPlayer.play(this.state.library[indexElement.innerHTML]);
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
                songRows.push(<SongRow song={obj} key={obj.path} index={obj.path}/>);
            }
        }
        return (
            <div id="song-list">
                {songRows}
            </div>
        );
    }
}

export default SongTable;