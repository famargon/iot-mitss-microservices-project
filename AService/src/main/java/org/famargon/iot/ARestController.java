package org.famargon.iot;

import java.net.UnknownHostException;
import java.util.UUID;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.MqttPersistenceException;
import org.famargon.iot.async.AsyncGreetResponse;
import org.famargon.iot.async.AsyncGreetService;
import org.famargon.iot.async.AsyncronousResponse;
import org.famargon.iot.async.CreateAsyncGreetResponse;
import org.famargon.iot.feign.BServiceProxy;
import org.famargon.iot.feign.SynchronousChainResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;

@RestController
public class ARestController {

	@Autowired
	Environment environment;
	@Autowired
	MqttClient mqttClient;
	@Autowired
	AsyncGreetService asyncGreetService;
	
	@Autowired
	BServiceProxy bServiceProxy;
	
	@HystrixCommand(fallbackMethod="chainGreetFallback")
	@GetMapping("/sync/greet")
	public SynchronousChainResult chainGreet() {
		SynchronousChainResult result = new SynchronousChainResult();
		result.setaGreeting("Hello from a-service");
		return bServiceProxy.syncGreet(result);
	}
	
	@HystrixCommand(fallbackMethod="chainGreetFallback")
	@GetMapping("/fallback/greet")
	public SynchronousChainResult fallbackGreet() {
		throw new RuntimeException("run fallback!!");
	}
	
	@HystrixCommand(fallbackMethod="asyncGreetFallback")
	@GetMapping("/async/greet/{greetId}")
	public AsyncronousResponse asyncGreet(@PathVariable(value="greetId") String greetId) throws UnknownHostException, MqttPersistenceException, JsonProcessingException, MqttException {
		return asyncGreetService.getGreet(greetId);
	}
	
	@PostMapping("/async/greet")
	public AsyncronousResponse startAsyncGreet() throws UnknownHostException, MqttPersistenceException, JsonProcessingException, MqttException {
		String uuid = UUID.randomUUID().toString();
		mqttClient.publish("/greet", new MqttMessage(new AsyncGreetResponse(uuid, "Hello from a-service").toJson()));
		return new CreateAsyncGreetResponse(uuid, environment.getProperty("server.port"));
	}
	
	public AsyncronousResponse asyncGreetFallback(String greetId) {
		CreateAsyncGreetResponse result = new CreateAsyncGreetResponse();
		result.setText("Se ha producido un error en el proceso");
		return result;
	}
	
	public SynchronousChainResult chainGreetFallback() {
		SynchronousChainResult result = new SynchronousChainResult();
		result.setaGreeting("Hello from fallback method on a-service");
		return result;
	}
	
	
}
