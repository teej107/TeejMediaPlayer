/**
 * Created by Tanner Norton on 5/4/2017.
 */
import React, {Component} from "react";
import Axios from "axios";
import SongRow from "./SongRow";
import test from "../Test";
import Clusterize from "clusterize.js";

class SongTable extends Component
{
    constructor(props)
    {
        super(props);
        this.state = {
            library: [],
            sort: "Songs"
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
        var indexElement = e.target;
        while (!indexElement.classList.contains("song-row"))
        {
            indexElement = indexElement.parentElement;
        }
        indexElement = indexElement.firstChild.innerHTML;
        this.audioPlayer.play(this.state.library[indexElement]);
    }

    onSortClick(e)
    {
        const sort = e.target.innerHTML;
        this.setState({sort: sort});
        switch (sort.toLowerCase())
        {
            case 'songs':
                break;
            case 'artist':
        }
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
            <div id="song-div">
                <div id="song-sort-div" onClick={this.onSortClick.bind(this)}>
                    <button className={this.state.sort === 'Songs' ? 'sort-selected' : null}>Songs</button>
                    <button className={this.state.sort === 'Artists' ? 'sort-selected' : null}>Artists</button>
                </div>
                <div id="song-list" onClick={this.onContentIdClick.bind(this)}>
                    {songRows}
                </div>
            </div>
        );
    }
}

export default SongTable;