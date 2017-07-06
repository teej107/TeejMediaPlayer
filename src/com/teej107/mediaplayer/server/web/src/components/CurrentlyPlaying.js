/**
 * Created by Tanner Norton on 5/12/2017.
 */
import React, {Component} from "react";
import ReactDOM from 'react-dom';
import Modal from "react-modal";
import TeejAlbum from "../../../../../../../assets/no-album-art.png";
import UpArrow from "../images/up arrow.svg";
import SongDescription from "./SongDescription";
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
        this.viewList =
            <ViewList navigator={props.navigator} audioPlayer={this.audioPlayer} hideModal={this.modalVisible.bind(this, false)}/>

        window.addEventListener('resize', this.calculateSize);
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

    componentDidMount = () =>
    {
        this.calculateSize();
    };

    calculateSize = () =>
    {
        var maxHeight = this.refs.playerView.offsetHeight;
        var dimension = (ref, width, height) =>
        {
            var styles = ReactDOM.findDOMNode(this.refs[ref]).style;
            styles.height = height + 'px';
            if(width > 0)
            {
                styles.width = width + 'px';
            }
            maxHeight -= height;
        };
        dimension('description', 0, 128);
        dimension('duration', 0, 70);
        dimension('playbackControls', 0, 150);

        var albumArt = this.refs.albumArt;
        maxHeight = Math.min(maxHeight, this.refs.playerView.offsetWidth);
        dimension('albumArt', albumArt.naturalWidth * maxHeight / albumArt.naturalHeight, maxHeight)
    };

    render()
    {
        return (
            <div id="player-view" ref="playerView">
                <SongDescription ref="description" audioPlayer={this.audioPlayer}/>
                <DurationSlider ref="duration" audioPlayer={this.audioPlayer}/>
                <img id="album-art" ref="albumArt" src={this.state.album} onClick={this.modalVisible.bind(this, true)}
                     onError={this.albumNotFound}/>
                <PlaybackControls ref="playbackControls" audioPlayer={this.audioPlayer}/>
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
