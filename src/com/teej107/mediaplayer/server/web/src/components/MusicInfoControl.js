/**
 * Created by Tanner Norton on 4/30/2017.
 */
import React, {Component} from "react";
import SongInfo from "./SongInfo";
import PlaybackControls from "./PlaybackControls";
import noAlbumArt from '../../../../../../../assets/no-album-art.png'

class MusicInfoControl extends Component
{
    constructor(props)
    {
        super(props);
        this.state = {
            album: ""
        };

        this.audioPlayer = props.audioPlayer;
        this.audioPlayer.songChangeListeners.push((song) => this.setState({album: "/api/album/" + song['album art']}));
    }

    render()
    {
        return (
            <div id="music-info-control">
                <SongInfo title="---" artist="---" album="---" audioPlayer={this.audioPlayer}/>
                <img src={this.state.album} className="album-art"/>
                <PlaybackControls audioPlayer={this.audioPlayer}/>
            </div>
        );
    }
}

export default MusicInfoControl;