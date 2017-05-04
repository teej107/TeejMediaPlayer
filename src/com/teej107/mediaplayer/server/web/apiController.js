/**
 * Created by Tanner Norton on 5/2/2017.
 */
const storage = require('./storage');

module.exports = {
    getSongFile: function (req, res)
    {
        var from = req.params[0];
        var song = storage.getSongFile(from);
        if (song)
        {
            res.sendFile(song, {root: ''});
        }
        else
        {
            res.status(404).send(from + " not found");
        }
    },
    getSongJSON: function (req, res)
    {
        var from = req.params[0];
        var json = storage.getSongJSON(from);
        res.send(json);
    }
};