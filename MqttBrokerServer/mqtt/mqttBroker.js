var mosca = require('mosca');

var mongodb = {
  type: 'mongo',
  url: process.env.MONGOURL,
  pubsubCollection: process.env.BROKERCOLLECTION,
  mongo: {}
};

var memory = {
    type: "memory"
}

var settings = {
  port: 1883
  //,backend: mongodb
};

module.exports = function(){

  var server = new mosca.Server(settings);

  server.on('clientConnected', function(client) {
      console.log('client connected', client.id);
  });

  // fired when a message is received
  server.on('published', function(packet, client) {
    //console.log('Published', packet.payload);
  });

  server.on('ready', setup);

  // fired when the mqtt server is ready
  function setup() {
    console.log('MQTT BROKER bounded at port '+settings.port);
  }

}