/**
 * Created by Tanner Norton on 5/6/2017.
 */
import React, {Component} from 'react';
import play from '../../../../../../../assets/button/play.png';
import playPress from '../../../../../../../assets/button/play press.png';
import pause from '../../../../../../../assets/button/pause.png';
import pausePress from '../../../../../../../assets/button/pause press.png';
import next from '../../../../../../../assets/button/next.png';
import nextPress from '../../../../../../../assets/button/next press.png';

class PlaybackControls extends Component
{
    constructor(props)
    {
        super(props);
        this.state = {
            previous: next,
            play : play,
            next: next
        }
        this.audioPlayer = props.audioPlayer;
    }

    setImage(key, image)
    {
        var obj = {};
        obj[key] = image;
        this.setState(obj);
    }

    render()
    {
        return (
            <div id="playback-controls">
                <img src={this.state.previous} className="flip" onMouseDown={this.setImage.bind(this, 'previous', nextPress)}
                     onMouseUp={this.setImage.bind(this, 'previous', next)} alt=""/>
                <img src={this.state.play} onMouseDown={this.setImage.bind(this, 'play', playPress)}
                     onMouseUp={this.setImage.bind(this, 'play', play)} alt=""/>
                <img src={this.state.next} onMouseDown={this.setImage.bind(this, 'next', nextPress)}
                     onMouseUp={this.setImage.bind(this, 'next', next)} alt=""/>
            </div>
        );
    }
}

export default PlaybackControls;