/**
 * Created by tanner on 6/1/17.
 */
import React from 'react';
import Axios from 'axios';
import test from '../Test';
import SongSort from './SongSort';
import ViewRow from "../components/ViewRow";
import SongRow from "../components/SongRow";
import ViewHistory from "../media/ViewHistory";

class Navigator
{
    constructor()
    {
        this.viewHistory = new ViewHistory();
    }

    key(key)
    {
        var keys = ["artist", "album", "title", "genre"];
        if (!key)
            return keys;
        var index = keys.indexOf(key.toLowerCase());
        if (index !== -1)
            return index === keys.length - 1 ? 0 : keys[index + 1];
    }


    search(viewBy, obj)
    {
        viewBy = viewBy.toLowerCase();
        var objKeys = this.key();
        if (viewBy === "root" || objKeys.indexOf(viewBy) < 0)
            return objKeys.map((e) => <ViewRow key={viewBy + e} display={e.toProperCase()}/>);

        if (this.getLibrary() === null)
            return [];
        const library = this.getLibrary().slice();

        var filter = (condition) =>
        {
            var i = 0, j = 0;
            while (i < library.length)
            {
                const val = library[i];
                if (condition(val, i, library)) library[j++] = val;
                i++;
            }

            library.length = j;
            return library;
        };

        var filterIf = (key) =>
        {
            if (obj.hasOwnProperty(key))
            {
                filter((e) => e[key] === obj[key]);
            }
        };

        if (obj)
        {
            objKeys.forEach((e) => filterIf(e));
        }
        if(viewBy === "title")
            return SongSort.sortBy(viewBy)(library).map((e, i) => <SongRow key={e.path} song={e} index={i}/> );
        return SongSort.viewBy(viewBy)(library).map((e) => <ViewRow key={viewBy + e} display={e}/>);
    }

    fetchLibrary(callback)
    {
        Axios.get("/api/library").then((response) =>
        {
            callback(this.libraryToArray(response.data));
        }, (failure) =>
        {
            callback(this.libraryToArray(test));
        });
    }

    loadLibrary(callback)
    {
        this.fetchLibrary((library) =>
        {
            this.getLibrary = () => library;
            if (callback instanceof Function)
            {
                callback(library);
            }
        });
    }

    getLibrary()
    {
        return null;
    }

    libraryToArray(library)
    {
        var arr = [];
        for (var e in library)
        {
            if (library.hasOwnProperty(e))
            {
                arr.push(library[e]);
            }
        }
        return arr;
    }
}

export default Navigator;