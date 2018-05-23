package com.mymq.sample.configuration;

import org.springframework.stereotype.Component;

@Component
public class Receiver {
	
	//@JmsListener(destination = "DEV.QUEUE.1")
    public void receiveMessage(CalimRequest calimRequest) {
        System.out.println("Received <" + calimRequest + ">");
    }

}
