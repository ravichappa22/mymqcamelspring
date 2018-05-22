package com.mymq.sample.configuration;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

@Component
public class Receiver {
	
	@JmsListener(destination = "DEV.QUEUE.1")
    public void receiveMessage(Email email) {
        System.out.println("Received <" + email + ">");
    }

}
