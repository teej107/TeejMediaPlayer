/**
 * Created by Tanner Norton on 5/2/2017.
 */
const storage = require('./storage');

module.exports = {
    getSongFile: function (req, res)
    {
        var from = req.params[0];
        var song = storage.getSong(from);
        if (song)
        {
            res.sendFile(song, {root: ''});
        }
        else
        {
            res.status(404).send(from + " not found");
        }
    }
};