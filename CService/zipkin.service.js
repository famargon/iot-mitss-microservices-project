module.exports = function(app){
    const constants = require('./service.constants');
    //zipkin tracing middelware
    const {Tracer, ExplicitContext, BatchRecorder} = require('zipkin');
    const {HttpLogger} = require('zipkin-transport-http');
    const zipkinMiddleware = require('zipkin-instrumentation-express').expressMiddleware;

    const ctxImpl = new ExplicitContext();
    //const recorder = new ConsoleRecorder();
    const recorder = new BatchRecorder({
        logger: new HttpLogger({
            endpoint: 'http://localhost:9411/api/v1/spans'
        })
    });
    const localServiceName = constants.SERVICE_NAME; // name of this application
    const tracer = new Tracer({ctxImpl, recorder, localServiceName});

    app.use(zipkinMiddleware({tracer}));
}


