/**
 * Created by Tanner Norton on 5/13/2017.
 */
import React, {Component} from 'react';
import Play from "../../../../../../../assets/button/play.png";
import PlayPress from "../../../../../../../assets/button/play press.png";
import Next from "../../../../../../../assets/button/next.png";
import NextPress from "../../../../../../../assets/button/next press.png";
import Pause from "../../../../../../../assets/button/pause.png";
import PausePress from "../../../../../../../assets/button/pause press.png";

class PlaybackControls extends Component
{
    constructor(props)
    {
        super(props);
        this.audioPlayer = props.audioPlayer;
    }

    style(img, imgPress)
    {
        return {
            width: '200px',
            height: '200px',
            backgroundImage: 'url(' + img + ')',
            ':active': {
                backgroundImage: 'url("' + imgPress + '")'
            }
        }
    }


    render()
    {
        return (
            <div id="playback-controls">
                <div className="hflip" style={this.style(Next, NextPress)}/>
                <div style={this.style(Play, PlayPress)}/>
                <div style={this.style(Next, NextPress)}/>
            </div>
        );
    }
}

export default PlaybackControls;