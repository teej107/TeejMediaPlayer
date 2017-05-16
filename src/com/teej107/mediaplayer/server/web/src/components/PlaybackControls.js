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
        this.state = {
            previous: Next,
            play: Play,
            next: Next
        };
        this.audioPlayer = props.audioPlayer;
    }

    changeState(state, value)
    {
        this.setState({[state]: value});
    }

    initButton(state, def, press)
    {
        return {
            src: def,
            onMouseDown: this.changeState.bind(this, state, press),
            onMouseUp: this.changeState.bind(this, state, def)
        }
    }

    render()
    {
        return (
            <div id="playback-controls">
                <img className="hflip" {...this.initButton('previous', Next, NextPress)} alt="previous"/>
                <img {...this.initButton('play', Play, PlayPress)} alt="play toggle"/>
                <img {...this.initButton('next', Next, NextPress)} alt="next"/>
            </div>
        );
    }
}

export default PlaybackControls;