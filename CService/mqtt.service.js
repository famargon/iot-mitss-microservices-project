module.exports = function(){
    var mqtt = require('mqtt');

    var client  = mqtt.connect('mqtt://localhost:1883');
 
    client.on('connect', function () {
        client.subscribe('/greet');
        console.log("CService server started...")  
    });
    
    client.on('message', function (topic, message) {
        // message is Buffer
        var greet = JSON.parse(message.toString());
        greet.cGreeting = "Hello from c-service in nodejs!!";
        client.publish('/greet/result',new Buffer(JSON.stringify(greet)));
        console.log("incomming message processed");
    });
}