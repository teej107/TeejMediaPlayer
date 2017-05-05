import React, {Component} from "react";
import SplitPane from "react-split-pane";
import MusicInfoControl from "./components/MusicInfoControl";
import AudioPlayer from "./media/AudioPlayer";
import "./resizer.css";
import SongTable from "./components/SongTable";

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
            <SplitPane split="vertical" minSize={document.documentElement.clientWidth / 6}
                       defaultSize={document.documentElement.clientWidth / 3}>
                <MusicInfoControl audioPlayer={this.audioPlayer}/>
                <SongTable audioPlayer={this.audioPlayer}/>
            </SplitPane>
        );
    }
}

export default App;
