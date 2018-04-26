package org.famargon.iot.async;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.annotation.PostConstruct;

import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AsyncGreetService {

	@Autowired
	private MqttClient mqttClient;
	
	private Map<String,AsyncGreetResponse> greets = new ConcurrentHashMap<>();
	
	@PostConstruct
	public void listen() throws MqttException {
		mqttClient.subscribe("/greet/result");
		mqttClient.setCallback(new MqttCallback() {
			
			@Override
			public void messageArrived(String topic, MqttMessage message) throws Exception {
				AsyncGreetResponse newGreet = AsyncGreetResponse.fromJson(message.getPayload());
				greets.put(newGreet.getGreetId(), newGreet);
			}
			
			@Override
			public void deliveryComplete(IMqttDeliveryToken token) {
			}
			
			@Override
			public void connectionLost(Throwable cause) {
			}
		});
	}
	
	public AsyncronousResponse getGreet(String greetId) {
		AsyncGreetResponse response = greets.get(greetId);
		if(response==null) {
			CreateAsyncGreetResponse cagr = new CreateAsyncGreetResponse();
			cagr.setText("ooops, parece que no se ha recibido todavía la respuesta");
			return cagr;
		}else {
			return response;
		}
	}
	
}
