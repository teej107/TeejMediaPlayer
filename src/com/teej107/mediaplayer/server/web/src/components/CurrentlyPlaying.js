/**
 * Created by Tanner Norton on 5/12/2017.
 */
import React, {Component} from "react";
import SongTable from "./SongTable";
import Modal from "react-modal";
import PlaybackControls from './PlaybackControls';
import TeejAlbum from "../../../../../../../assets/no-album-art.png";

class CurrentlyPlaying extends Component
{
    constructor(props)
    {
        super(props);
        this.state = {
            song: {},
            modal: false
        };
        this.audioPlayer = props.audioPlayer;
        this.audioPlayer.songChangeListeners.push(this.onSongChange.bind(this));
    }

    modalVisible(bool)
    {
        this.setState({modal: bool});
    }

    onSongChange(song)
    {
        this.setState(
            {
                song: song,
                modal: false
            });
    }

    defaultValue(value, ifFalsey)
    {
        return value ? value : ifFalsey;
    }


    render()
    {
        return (
            <div id="currently-playing">
                <p>{this.defaultValue(this.state.song.title, '---')}</p>
                <p>{this.defaultValue(this.state.song.artist, '---')}</p>
                <p>{this.defaultValue(this.state.song.album, '---')}</p>
                <img id="album-art" src={this.defaultValue(this.state.song['album art'], TeejAlbum)}
                     onClick={this.modalVisible.bind(this, true)}/>
                <PlaybackControls audioPlayer={this.audioPlayer}/>

                <Modal className="modal" isOpen={this.state.modal} contentLabel="Library">
                    <div className="close-bar" onClick={this.modalVisible.bind(this, false)}></div>
                    <SongTable audioPlayer={this.audioPlayer}/>
                </Modal>
            </div>
        );
    }
}

export default CurrentlyPlaying;