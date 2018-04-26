package org.famargon.iot.async;

import java.io.IOException;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class AsyncGreetResponse implements AsyncronousResponse{

	private String greetId;
	private String aGreeting;
	private String cGreeting;

	public AsyncGreetResponse() {
		//para el parser json
	}
	
	public AsyncGreetResponse(String uuid, String aGreeting) {
		this.greetId = uuid;
		this.aGreeting = aGreeting;
	}
	
	public String getGreetId() {
		return greetId;
	}

	public void setGreetId(String greetId) {
		this.greetId = greetId;
	}

	public String getaGreeting() {
		return aGreeting;
	}
	public void setaGreeting(String aGreeting) {
		this.aGreeting = aGreeting;
	}
	public String getcGreeting() {
		return cGreeting;
	}
	public void setcGreeting(String cGreeting) {
		this.cGreeting = cGreeting;
	}
	
	@JsonIgnore
	public byte[] toJson() throws JsonProcessingException {
		return new ObjectMapper().writeValueAsBytes(this);
	}
	
	public static AsyncGreetResponse fromJson(byte[] json) throws IOException {
		return new ObjectMapper().readValue(json, AsyncGreetResponse.class);
	}
	
}
