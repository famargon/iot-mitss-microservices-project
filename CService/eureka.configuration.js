module.exports = function(){
    const constants = require('./service.constants');
    const Eureka = require('eureka-js-client').Eureka;
    
    const statusPageUrl = 'http://localhost:'+constants.SERVER_PORT+'/status';
    
    const eureka = new Eureka({
      instance: {
        id: constants.SERVICE_NAME,    
        instanceId: constants.SERVICE_NAME,
        app: constants.SERVICE_NAME,
        hostName: 'localhost',
        ipAddr: '127.0.0.1',
        statusPageUrl: statusPageUrl,
        port: {
          '$': constants.SERVER_PORT,
          '@enabled': 'true',
        },
        vipAddress: constants.SERVICE_NAME,
        dataCenterInfo: {
          '@class': 'com.netflix.appinfo.InstanceInfo$DefaultDataCenterInfo',
          name: 'MyOwn',
        }
      },
      eureka: {
        host: 'localhost',
        port: 8761,
        servicePath: '/eureka/apps/'
      }
    });
    eureka.logger.level('debug');
    eureka.start(function(error){
      console.log(error || 'complete');
    });
}