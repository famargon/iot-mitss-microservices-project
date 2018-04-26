const constants = require('./service.constants');
var express = require('express');
var bodyParser = require('body-parser');
var eureka = require('./eureka.configuration');
var zipkin = require('./zipkin.service');

var app = express();

app.use(bodyParser.urlencoded({extended: false}));
app.use(bodyParser.json());

//eureka registration
eureka();
//zipkin tracing middelware
zipkin(app);

//status api for eureka
app.all('/status', function (req, res) {
    console.log("status request received")
    res.status(200);
    res.end();
});

app.listen(constants.SERVER_PORT, function () {
  console.log('Listening at port: '+ constants.SERVER_PORT);
});

//our businesslogic, mqtt based async communication
var mqtt = require('./mqtt.service')();