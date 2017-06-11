/**
 * Created by Tanner Norton on 6/8/2017.
 */
import React, {Component} from "react";
import AudioPlayer from "../media/AudioPlayer";

class DurationSlider extends Component
{
    constructor(props)
    {
        super(props);
        this.audioPlayer = props.audioPlayer;
        this.audioPlayer.songChangeListeners.push(this.onSongChange);
        this.audioPlayer.songTimeUpdateListeners.push(this.onTimeChange);
        this.dragging = false;
        this.valueChanged = false;
        this.state = {
            value: 0,
            max: 0
        };
    }

    onSongChange = (song) =>
    {
        this.setState({max: song.duration, value: 0});
    };

    onTimeChange = (time) =>
    {
        if (!this.dragging)
        {
            this.setState({value: time});
        }
    };

    handleEvent = (event) =>
    {
        this.valueChanged = true;
        this.setState({value: event.target.value});
    };

    initInput = () =>
    {
        return {
            type: "range",
            max: this.state.max,
            value: this.state.value,
            onChange: this.handleEvent,
            onMouseDown: () => this.dragging = true,
            onMouseUp: () =>
            {
                if(this.valueChanged)
                {
                    this.audioPlayer.mediaPlayer.currentTime = this.state.value;
                }
                this.dragging = false;
                this.valueChanged = false;
            }
        };
    };

    render()
    {
        return (
            <div id="duration-slider">
                <p>{AudioPlayer.formatDuration(this.state.value)}</p>
                <input {...this.initInput()}/>
                <p>{AudioPlayer.formatDuration(this.state.max - this.state.value)}</p>
            </div>
        );
    }
}

export default DurationSlider;