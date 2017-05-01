/**
 * Created by tanner on 4/22/17.
 */
var port = getPort();

var express = require('express');
var app = express();
var storage = require('./storage');

app.listen(port, function ()
{
    console.log("listening on port", port);
});

app.use(express.static(__dirname +  '/build'));

function varargParams(initPath)
{
    return /^\// + initPath + /\/(.*)/;
}

app.get(varargParams('media'), function (req, res)
{
    var from = req.params[0];
    var song = storage.getSong(from);
    if(song)
    {
        res.sendFile(song, {root: ''});
    }
    else
    {
        res.status(404).send(from + " not found");
    }
});


