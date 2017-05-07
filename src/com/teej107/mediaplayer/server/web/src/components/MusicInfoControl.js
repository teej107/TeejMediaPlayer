/**
 * Created by Tanner Norton on 4/30/2017.
 */
import React, {Component} from "react";
import SongInfo from './SongInfo';
import PlaybackControls from './PlaybackControls';

class MusicInfoControl extends Component
{
    constructor(props)
    {
        super(props);
        this.audioPlayer = props.audioPlayer;
    }

    render()
    {
        return (
            <div id="music-info-control">
                <SongInfo title="---" artist="---" album="---" audioPlayer={this.audioPlayer}/>
                <PlaybackControls audioPlayer={this.audioPlayer}/>
            </div>
        );
    }
}

export default MusicInfoControl;