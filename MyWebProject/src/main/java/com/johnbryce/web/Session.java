package com.johnbryce.web;

import com.johnbryce.services.ClientFacade;

public class Session {
	private ClientFacade facade;
	private long lastAccessed;
	
	public Session(ClientFacade facade, long lastAccessed) {
		super();
		this.facade = facade;
		this.lastAccessed = lastAccessed;
	}

	public ClientFacade getFacade() {
		return facade;
	}

	public long getLastAccessed() {
		return lastAccessed;
	}

	public void setLastAccessed(long lastAccessed) {
		this.lastAccessed = lastAccessed;
	}
	
	
	
	
	
	

}
