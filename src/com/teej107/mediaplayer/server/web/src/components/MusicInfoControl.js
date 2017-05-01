/**
 * Created by Tanner Norton on 4/30/2017.
 */
import React, {Component} from "react";
import SongInfo from './SongInfo';

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
            <div>
                <SongInfo title="---" artist="---" album="---"/>
            </div>
        );
    }
}

export default MusicInfoControl;