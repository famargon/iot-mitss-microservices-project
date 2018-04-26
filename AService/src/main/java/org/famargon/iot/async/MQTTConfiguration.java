package org.famargon.iot.async;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

@Order(1)
@Configuration
public class MQTTConfiguration {

	private String getMqttUri(){
		return "tcp://"+"localhost"+":"+"1883";
	}
	
	@Bean
	public MqttClient mqttClient() throws MqttException {
	    MqttClient mqttClient = new MqttClient(getMqttUri(), "a-service-client-id");
	    MqttConnectOptions connOptions = new MqttConnectOptions();
	    connOptions.setUserName("admin");
	    connOptions.setPassword("admin".toCharArray());
	    mqttClient.connect(connOptions);
	    return mqttClient;
	}
}
