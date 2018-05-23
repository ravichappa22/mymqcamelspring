package com.mymq.sample.configuration;

import javax.jms.ConnectionFactory;

import org.apache.camel.component.jms.JmsComponent;
import org.springframework.beans.factory.annotation.Value;
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
	
	@Value("${messaging.mq.host}")
	private String host;
	@Value("${messaging.mq.port}")
	private int port;
	@Value("${messaging.mq.channel}")
	private String channel;
	@Value("${messaging.mq.ccsid}")
	private int ccsid;
	@Value("${messaging.mq.queueManager}")
	private String queueManager;
	@Value("${messaging.mq.messaging.user}")
	private String username;
	@Value("${messaging.mq.messaging.password}")
	private String password;
	
	
	@Bean
	public MQQueueConnectionFactory mqQueueConnectionFactory() {
	    MQQueueConnectionFactory mqQueueConnectionFactory = new MQQueueConnectionFactory();
	    mqQueueConnectionFactory.setHostName(host);
	    try {
	        mqQueueConnectionFactory.setTransportType(WMQConstants.WMQ_CM_CLIENT);
	        mqQueueConnectionFactory.setCCSID(ccsid); //this should be parameterized, we should get this number from MQ admin
	        mqQueueConnectionFactory.setChannel(channel);
	        mqQueueConnectionFactory.setPort(port);
	        mqQueueConnectionFactory.setQueueManager(queueManager);
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return mqQueueConnectionFactory;
	}
	
	
	@Bean
	public UserCredentialsConnectionFactoryAdapter userCredentialsConnectionFactoryAdapter(MQQueueConnectionFactory mqQueueConnectionFactory) {
	    UserCredentialsConnectionFactoryAdapter userCredentialsConnectionFactoryAdapter = new UserCredentialsConnectionFactoryAdapter();
	    userCredentialsConnectionFactoryAdapter.setUsername(username);
	    userCredentialsConnectionFactoryAdapter.setPassword(password);
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
