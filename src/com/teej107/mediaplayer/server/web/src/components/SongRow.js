/**
 * Created by Tanner Norton on 5/4/2017.
 */
import React, {Component} from "react";

class SongRow extends Component {
    constructor(props) {
        super(props);
        this.state = props.song;
    }

    render() {
        return (
            <div className="song-row">
                <p className="index">{this.props.index}</p>
                <div>
                    <p> { this.state.title } </p>
                    <p> { this.state.artist } </p>
                </div>
                <div>
                    <p> { this.state.album } </p>
                    <p> {this.state.duration} </p>
                </div>
            </div>
        );
    }
}

export default SongRow;