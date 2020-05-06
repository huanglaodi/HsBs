package com.hs.Device.utils.servlet;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import com.hs.Device.utils.db.DBConnectionManager;

public class ContextListener implements ServletContextListener{
	
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("contextInitialized");
		DBConnectionManager.getInstance();
		DeviceSatatusServer.getInstance();
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		DeviceSatatusServer.getInstance().close();
		DBConnectionManager.getInstance().release();
		System.out.println("contextDestroyed");
	}

}
