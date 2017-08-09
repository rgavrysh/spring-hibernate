const express = require("express");
const https = require("https");
const app = express();
var fs = require('fs');
var sslOptions = {
  key: fs.readFileSync('key.pem'),
  cert: fs.readFileSync('cert.pem'),
  passphrase: 'qwe123'
};
app.use(express.static(__dirname + '/dist'));

app.listen(process.env.PORT || 4200);
https.createServer(sslOptions, app);
