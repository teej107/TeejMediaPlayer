/**
 * Created by tanner on 4/22/17.
 */
var port = getPort();

var express = require('express');
var app = express();
app.listen(port, function () {
    console.log("listening on port", port);
});

app.get('/media', function (req, res) {
   res.send("Teej Media Player");
});