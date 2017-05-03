/**
 * Created by tanner on 4/22/17.
 */
const port = j_getPort();
const shutdownKey = j_shutdownKey();

const express = require('express');
const app = express();
const apiController = require('./apiController');

const server = app.listen(port, function ()
{
    console.log("listening on port", port);
    console.log("Shutdown Key:", shutdownKey);
});

app.use(express.static(__dirname + '/build'));

function varargParams(initPath)
{
    return /^\// + initPath + /\/(.*)/;
}

/**
 * Should only be used by application to gracefully stop node.
 */
app.get('/' + shutdownKey, function (req, res)
{
    var close = 'closing server...';
    res.status(200).send(close);
    console.log(close);
    server.close(function ()
    {
        console.log('server closed');
    });
});

app.get("/test/:id", apiController.getSongFile);

app.get('/sanity', function (req, res)
{
   res.send("Yup it works!")
});


