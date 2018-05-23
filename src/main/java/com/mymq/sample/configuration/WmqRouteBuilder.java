package com.mymq.sample.configuration;

import javax.jms.ConnectionFactory;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.jms.JmsComponent;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class WmqRouteBuilder extends RouteBuilder {

	@Bean("jms")
	public JmsComponent jmsComponent(ConnectionFactory factory) {
		JmsComponent jmsComponent = new JmsComponent();
		jmsComponent.setConnectionFactory(factory);
		return jmsComponent;	
	}
		
	@Override
	public void configure() throws Exception {
		from("jms:queue:DEV.QUEUE.1?concurrentConsumers=1&exchangePattern=InOnly&preserveMessageQos=true&includeSentJMSMessageID=true").to("bean:receiver?method=receiveMessage")
				.to("jms:queue:DEV.QUEUE.2");
	}

}
