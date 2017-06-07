/**
 * Created by Tanner Norton on 5/12/2017.
 */
import React, {Component} from "react";
import SongTable from "./ViewList";
import Modal from "react-modal";
import PlaybackControls from './PlaybackControls';
import TeejAlbum from "../../../../../../../assets/no-album-art.png";
import UpArrow from '../images/up arrow.svg';
import SongInfo from "./SongInfo";

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
        this.audioPlayer.songChangeListeners.push(this.onSongChange.bind(this));

        this.viewList = props.viewlist;
    }

    modalVisible(bool)
    {
        this.setState({modal: bool});
    }

    onSongChange(song)
    {
        this.setState(
            {
                album: 'api/album/' + song['album art'],
                modal: false
            });
    }

    albumNotFound(e)
    {
        e.target.src = TeejAlbum;
    }

    render()
    {
        return (
            <div id="currently-playing">
                <SongInfo audioPlayer={this.audioPlayer} title="---" artist="---" album="---"/>
                <img id="album-art" src={this.state.album}
                     onClick={this.modalVisible.bind(this, true)} onError={this.albumNotFound}/>
                <PlaybackControls audioPlayer={this.audioPlayer}/>

                <Modal className="modal" isOpen={this.state.modal} contentLabel="Library">
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
