package com.mymq.sample.configuration;

import javax.jms.ConnectionFactory;

import org.apache.camel.component.jms.JmsComponent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jms.connection.CachingConnectionFactory;
import org.springframework.jms.connection.JmsTransactionManager;
import org.springframework.jms.connection.UserCredentialsConnectionFactoryAdapter;
import org.springframework.transaction.PlatformTransactionManager;

import com.ibm.mq.jms.MQQueueConnectionFactory;
import com.ibm.msg.client.wmq.WMQConstants;

@Configuration
public class C360ConnectionConfiguration {
	
	
	
	@Bean
	public MQQueueConnectionFactory mqQueueConnectionFactory() {
	    MQQueueConnectionFactory mqQueueConnectionFactory = new MQQueueConnectionFactory();
	    mqQueueConnectionFactory.setHostName("localhost");
	    try {
	        mqQueueConnectionFactory.setTransportType(WMQConstants.WMQ_CM_CLIENT);
	        mqQueueConnectionFactory.setCCSID(1208); //this should be parameterized, we should get this number from MQ admin
	        mqQueueConnectionFactory.setChannel("DEV.ADMIN.SVRCONN");
	        mqQueueConnectionFactory.setPort(1414);
	        mqQueueConnectionFactory.setQueueManager("QM1");
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return mqQueueConnectionFactory;
	}
	
	
	@Bean
	public UserCredentialsConnectionFactoryAdapter userCredentialsConnectionFactoryAdapter(MQQueueConnectionFactory mqQueueConnectionFactory) {
	    UserCredentialsConnectionFactoryAdapter userCredentialsConnectionFactoryAdapter = new UserCredentialsConnectionFactoryAdapter();
	    userCredentialsConnectionFactoryAdapter.setUsername("admin");
	    userCredentialsConnectionFactoryAdapter.setPassword("passw0rd");
	    userCredentialsConnectionFactoryAdapter.setTargetConnectionFactory(mqQueueConnectionFactory);
	    return userCredentialsConnectionFactoryAdapter;
	}
	
	@Bean
	@Primary
	public ConnectionFactory cachingConnectionFactory(UserCredentialsConnectionFactoryAdapter userCredentialsConnectionFactoryAdapter) {
	    CachingConnectionFactory cachingConnectionFactory = new CachingConnectionFactory();
	    cachingConnectionFactory.setTargetConnectionFactory(userCredentialsConnectionFactoryAdapter);
	    cachingConnectionFactory.setSessionCacheSize(500);
	    cachingConnectionFactory.setReconnectOnException(true);
	    return (ConnectionFactory)cachingConnectionFactory;
	}
	
	@Bean
    public PlatformTransactionManager jmsTransactionManager(ConnectionFactory connectionFactory) {
        JmsTransactionManager jmsTransactionManager = new JmsTransactionManager();
        jmsTransactionManager.setConnectionFactory(connectionFactory);
        return jmsTransactionManager;
    }
	
	@Bean("jms")
	public JmsComponent jmsComponent(ConnectionFactory factory) {
		JmsComponent jmsComponent = new JmsComponent();
		jmsComponent.setConnectionFactory(factory);
		return jmsComponent;	
	}

}
