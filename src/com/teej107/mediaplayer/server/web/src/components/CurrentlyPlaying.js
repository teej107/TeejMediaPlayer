/**
 * Created by Tanner Norton on 5/12/2017.
 */
import React, {Component} from "react";
import Modal from "react-modal";
import TeejAlbum from "../../../../../../../assets/no-album-art.png";
import UpArrow from '../images/up arrow.svg';
import SongInfo from "./SongInfo";
import DurationSlider from "./DurationSlider";
import ViewList from "./ViewList";
import PlaybackControls from "./PlaybackControls";

class CurrentlyPlaying extends Component
{
    constructor(props)
    {
        super(props);
        this.state = {
            album: TeejAlbum,
            modal: false
        };
        this.audioPlayer = props.audioPlayer;
        this.audioPlayer.songChangeListeners.push(this.onSongChange);
        this.viewList = <ViewList navigator={props.navigator} audioPlayer={this.audioPlayer} hideModal={this.modalVisible.bind(this, false)}/>
    }

    modalVisible = (bool) =>
    {
        this.setState({modal: bool});
    };

    onSongChange = (song) =>
    {
        this.setState(
            {
                album: song.hasOwnProperty('album art') ? 'api/album/' + song['album art'] : TeejAlbum
            });
    };

    albumNotFound = (e) =>
    {
        e.target.src = TeejAlbum;
    };

    render()
    {
        return (
            <div id="currently-playing">
                <SongInfo audioPlayer={this.audioPlayer}/>
                <DurationSlider audioPlayer={this.audioPlayer}/>
                <img id="album-art" src={this.state.album}
                     onClick={this.modalVisible.bind(this, true)} onError={this.albumNotFound}/>
                <PlaybackControls audioPlayer={this.audioPlayer}/>
                <Modal className="modal" overlayClassName="modal-overlay" isOpen={this.state.modal} contentLabel="Library">
                    <div className="close-bar clickable" onClick={this.modalVisible.bind(this, false)}>
                        <img src={UpArrow}/>
                    </div>
                    {this.viewList}
                </Modal>
            </div>
        );
    }
}

export default CurrentlyPlaying;
