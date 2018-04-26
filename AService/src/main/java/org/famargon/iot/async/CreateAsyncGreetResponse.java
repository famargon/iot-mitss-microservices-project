package org.famargon.iot.async;

import java.net.UnknownHostException;

public class CreateAsyncGreetResponse implements AsyncronousResponse{

	private String greetId;
	private String text;
	
	public CreateAsyncGreetResponse() {
		//para el parser json
	}
	
	public CreateAsyncGreetResponse(String uuid, String port) throws UnknownHostException {
		greetId = uuid;
		text = "No olvides usar el access token para consultar el resultado de este greetId";
	}

	public String getGreetId() {
		return greetId;
	}

	public void setGreetId(String greetId) {
		this.greetId = greetId;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

}
