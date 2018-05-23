package com.mymq.sample.configuration;

import org.springframework.stereotype.Component;

@Component
public class Receiver {
	
	
    public void receiveMessage(CalimRequest calimRequest) {
        System.out.println("Received in Queue1 from camel route<" + calimRequest + ">");
    }
    
    //Uncomment Comment out the below line code to see the message in Queue2
   // @JmsListener(destination = "DEV.QUEUE.2")
    public void receiveMessageQueue2(CalimRequest calimRequest) {
        System.out.println("Received Message from Queue 2 <" + calimRequest + ">");
    }
    

}
