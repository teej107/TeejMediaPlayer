/**
 * Created by Tanner Norton on 5/4/2017.
 */
import React, {Component} from "react";
import Clusterize from "clusterize.js";
import UpArrow from '../images/up arrow.svg';

class ViewList extends Component
{
    constructor(props)
    {
        super(props);
        this.audioPlayer = props.audioPlayer;
        this.navigator = props.navigator;
        this.viewHistory = this.navigator.viewHistory;
        this.state = {
            library: [],
            backClass: (bool) => bool ? "clickable" : "clickable active"
        };
    }

    componentWillMount()
    {
        if (this.navigator.getLibrary() === null)
        {
            this.navigator.loadLibrary(() => this.updateView());
        }
        else
        {
            this.updateView();
        }
    }

    updateView(historyFn = this.viewHistory.current)
    {
        if (historyFn instanceof Function)
        {
            var result = historyFn();
            var obj = result.hasOwnProperty("search") ? result.search : null;
            this.setState({library: this.navigator.search(result.view, obj)});
        }
    }

    componentDidMount()
    {
        // this.clusterize = new Clusterize({
        //     scrollId: 'song-list',
        //     contentId: 'song-list',
        //     show_no_data_row: false
        // });
    }

    onContentIdClick(e)
    {
        var target = e.target;
        if (target === e.currentTarget)
            return;

        while (!target.classList.contains("row"))
        {
            target = target.parentElement;
        }
        var index = target.getElementsByTagName("index")[0].innerHTML;
        if (target.classList.contains("song-row"))
        {
            this.audioPlayer.play(this.state.library[index].props.song);
        }
        else
        {
            if (this.viewHistory.isRoot())
            {
                this.updateView(this.viewHistory.up.bind(this.viewHistory, index))
            }
            else
            {
                var current = this.viewHistory.current();
                var search = current.search ? current.search : {};
                var nextView = this.navigator.key(current.view);
                search[current.view] = index;
                console.log("current:", current.view, current);
                console.log("next:", nextView, search);
                this.updateView(this.viewHistory.up.bind(this.viewHistory, nextView, search));
            }

        }
    }

    render()
    {
        return (
            <div id="song-div">
                <div id="song-div-back" className={this.state.backClass(this.viewHistory.isRoot())}
                     onClick={this.updateView.bind(this, this.viewHistory.back)}>
                    <img src={UpArrow}/>
                </div>
                <div id="song-list" onClick={this.onContentIdClick.bind(this)}>
                    {this.state.library}
                </div>
            </div>
        );
    }
}

export default ViewList;