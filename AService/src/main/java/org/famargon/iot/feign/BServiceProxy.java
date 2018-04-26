package org.famargon.iot.feign;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name="b-service")
@RibbonClient(name="b-service")
public interface BServiceProxy {

	@PostMapping("/sync/greet/")
	public SynchronousChainResult syncGreet(@RequestBody SynchronousChainResult result);
	
}
