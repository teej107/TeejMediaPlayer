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
        var rawData = j_getSongJSON(path);
        return JSON.parse(rawData);
    }
};