package com.mymq.sample.configuration;

import org.apache.camel.builder.RouteBuilder;
import org.springframework.stereotype.Component;

@Component
public class WmqRouteBuilder extends RouteBuilder {
		
	@Override
	public void configure() throws Exception {
		from("jms:DEV.QUEUE.1?concurrentConsumers=1&exchangePattern=InOnly&preserveMessageQos=true&includeSentJMSMessageID=true")
						.to("bean:receiver?method=receiveMessage")
						//here erich call Restful service to get the details and Marshal to an object that can be pushed to next queue
				.to("jms:queue:DEV.QUEUE.2");
	}

}
