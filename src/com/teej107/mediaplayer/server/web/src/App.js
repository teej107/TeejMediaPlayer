import React, {Component} from "react";
import AudioPlayer from "./media/AudioPlayer";
import Navigator from "./media/Navigator";
import CurrentlyPlaying from "./components/CurrentlyPlaying";
import ViewList from './components/ViewList';

class App extends Component
{
    constructor()
    {
        super();
        this.audioPlayer = new AudioPlayer();
        this.navigator = new Navigator();
        this.viewList = <ViewList navigator={this.navigator} audioPlayer={this.audioPlayer}/>
    }

    render()
    {
        return <CurrentlyPlaying audioPlayer={this.audioPlayer} viewlist={this.viewList}/>;
    }
}

String.prototype.toProperCase = function ()
{
    return this.toUpperCase().substring(0, 1) + this.substring(1).toLowerCase()
};

export default App;
