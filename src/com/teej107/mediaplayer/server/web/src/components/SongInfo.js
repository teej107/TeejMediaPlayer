/**
 * Created by Tanner Norton on 4/30/2017.
 */
import React, { Component } from 'react';

class SongInfo extends Component
{
    constructor(props)
    {
        super(props);
        this.state = {
            title: props.title,
            artist: props.artist,
            album: props.album
        }
    }

    render()
    {
        return (
          <div>
              <p>{ this.state.title }</p>
              <p>{ this.state.artist }</p>
              <p>{ this.state.album }</p>
          </div>
        );
    }
}

export default SongInfo