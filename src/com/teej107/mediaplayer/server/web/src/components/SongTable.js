/**
 * Created by Tanner Norton on 5/4/2017.
 */
import React, {Component} from "react";
import Axios from "axios";
import SongRow from "./SongRow";
import test from "../Test";
import Clusterize from "clusterize.js";
import SongSort from "../media/SongSort";

class SongTable extends Component
{
    constructor(props)
    {
        super(props);
        this.songSorts = {};
        var toSongSorts = (...sort) =>
        {
            sort.forEach((e) => this.songSorts[e.getSortName()] = e);
        };
        toSongSorts(new SongSort("Songs", SongSort.sortBy("title")), new SongSort("Artists", SongSort.sortBy("artist")));

        this.state = {
            library: [],
            sort: this.songSorts.Songs
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
        const songSort = this.songSorts[sort];
        const newLib = songSort.getCallback()(this.state.library);
        console.log(newLib);
        this.setState({
            sort: songSort,
            library: songSort.getCallback()(this.state.library)
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
                songRows.push(<SongRow song={obj} key={obj.path} index={obj.path}/>);
            }
        }
        return (
            <div id="song-div">
                <div id="song-sort-div" onClick={this.onSortClick.bind(this)}>
                    <button className={this.state.sort.getSortName() === 'Songs' ? 'sort-selected' : null}>Songs</button>
                    <button className={this.state.sort.getSortName() === 'Artists' ? 'sort-selected' : null}>Artists</button>
                </div>
                <div id="song-list" onClick={this.onContentIdClick.bind(this)}>
                    {songRows}
                </div>
            </div>
        );
    }
}

export default SongTable;