import React, {Component} from "react";
import SplitPane from 'react-split-pane';
import MusicInfoControl from "./components/MusicInfoControl";
import AudioPlayer from './media/AudioPlayer';
import './resizer.css';

class App extends Component
{
    constructor()
    {
        super();
        this.audioPlayer = new AudioPlayer();
    }

    render()
    {
        return (
            <SplitPane split="vertical" minSize={50} defaultSize={100}>
                <MusicInfoControl audioPlayer={this.audioPlayer}/>
                <div></div>
            </SplitPane>
        );
    }
}

export default App;
