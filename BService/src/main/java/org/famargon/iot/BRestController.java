package org.famargon.iot;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class BRestController {

	@PostMapping("/sync/greet/")
	public SynchronousChainResult syncGreet(@RequestBody SynchronousChainResult result) {
		result.setbGreeting("Hello from b-service");
		return result;
	}
	
}
