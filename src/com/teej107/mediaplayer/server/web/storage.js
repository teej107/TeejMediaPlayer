/**
 * Created by Tanner Norton on 4/30/2017.
 */
module.exports = {
    getSongFile: function (path)
    {
        return j_getFile(path);
    },
    getSongJSON: function (path)
    {
        return JSON.parse(j_getSongJSON(path));
    },
    getLibrary: function ()
    {
        return JSON.parse(j_getLibrary());
    },
    getAlbumArt: function (artist, album)
    {
        return j_getAlbumArt(artist, album);
    }
};