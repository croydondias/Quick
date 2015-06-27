package com.croydon.quick.xxx;

import java.net.InetAddress;

import javax.annotation.PostConstruct;

import org.apache.log4j.Logger;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix="connection")
public class ApplicationSetup {
   
	private static final Logger LOG = Logger.getLogger(ApplicationSetup.class);
 
    private String username;
 
    private InetAddress remoteAddress;
 
	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public InetAddress getRemoteAddress() {
		return remoteAddress;
	}

	public void setRemoteAddress(InetAddress remoteAddress) {
		this.remoteAddress = remoteAddress;
	}

	@Override
	public String toString() {
		return username + " : " + remoteAddress;
	}

	@PostConstruct
	public void xxx() {
		LOG.info(String.format("Initialized [%s] [%s]", username, remoteAddress));
	}
   
   
}
