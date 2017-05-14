import React, {Component} from "react";
import AudioPlayer from "./media/AudioPlayer";
import CurrentlyPlaying from "./components/CurrentlyPlaying";

class App extends Component
{
    constructor()
    {
        super();
        this.audioPlayer = new AudioPlayer();
    }

    render()
    {
        return <CurrentlyPlaying audioPlayer={this.audioPlayer}/>;
    }
}

export default App;
