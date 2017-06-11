/**
 * Created by Tanner Norton on 5/13/2017.
 */
import React, {Component} from "react";
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
        this.audioPlayer.songPlaybackListeners.push((bool) => this.setState({play: bool ? Pause : Play}));
    }

    changeState = (state, value) =>
    {
        return () =>
        {
            if (this.state[state] !== value)
            {
                this.setState({[state]: value});
            }
        };
    };

    initButton = (state, def, press) =>
    {
        var obj = {
            src: this.state[state],
            onMouseDown: this.changeState(state, press),
            onMouseUp: this.changeState(state, def),
            onMouseLeave: this.changeState(state, def),
            alt: state
        };
        return obj;
    };

    playImg = (press) =>
    {
        if (this.audioPlayer.isPlaying())
            return press ? PausePress : Pause;
        return press ? PlayPress : Play;
    };

    render()
    {
        return (
            <div id="playback-controls">
                <img className="hflip" {...this.initButton('previous', Next, NextPress)}
                     onClick={() => this.audioPlayer.previous(false)}/>
                <img {...this.initButton('play', this.playImg(false), this.playImg(true))}
                     onClick={this.audioPlayer.toggle}/>
                <img {...this.initButton('next', Next, NextPress)}
                     onClick={() => this.audioPlayer.next(false, true)}/>
            </div>
        );
    }
}

export default PlaybackControls;