import React, {Component} from "react";
import AudioPlayer from "./media/AudioPlayer";
import Navigator from "./media/Navigator";
import CurrentlyPlaying from "./components/CurrentlyPlaying";
import Playlist from "./media/Playlist";

class App extends Component
{
    constructor()
    {
        super();
        this.playlist = new Playlist();
        this.audioPlayer = new AudioPlayer();
        this.audioPlayer.songPlaybackListeners.push((bool) =>
        {
            App.title(bool);
        });

        this.navigator = new Navigator();
    }

    static title(bool)
    {
        if (bool === undefined || bool === null)
        {
            window.document.title = "Teej Media Player";
        }
        else
        {
            window.document.title = "Teej Media Player | " + (bool ? "►" : "‖");
        }
    }

    render()
    {
        return <CurrentlyPlaying audioPlayer={this.audioPlayer} navigator={this.navigator}/>;
    }
}

String.prototype.toProperCase = function ()
{
    return this.toUpperCase().substring(0, 1) + this.substring(1).toLowerCase()
};

Array.prototype.shallowEquals = function (arr)
{
    if (!arr instanceof Array)
        return false;
    if (this.length !== arr.length)
        return false;

    for (var i = 0; i < this.length; i++)
    {
        if (this[i] !== arr[i])
            return false;
    }

    return true;
};

export default App;
