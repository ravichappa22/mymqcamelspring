package com.mymq.sample;

import java.time.Instant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.jms.core.JmsTemplate;

import com.mymq.sample.configuration.CalimRequest;

@SpringBootApplication
@EnableJms
public class MymqcamelspringApplication {

	public static void main(String[] args) {
		  // Launch the application
        ConfigurableApplicationContext context = SpringApplication.run(MymqcamelspringApplication.class, args);

        JmsTemplate jmsTemplate = context.getBean(JmsTemplate.class);

        // Send a message with a POJO - the template reuse the message converter
        System.out.println("Sending a claim request message.");
        jmsTemplate.convertAndSend("DEV.QUEUE.1", new CalimRequest("12345", Instant.now(), Instant.now()));
	}
}
