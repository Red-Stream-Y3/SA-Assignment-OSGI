package com.redstream.followerserviceconsumer;

import com.redstream.followerservicemanager.FriendsManager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

public class ServiceActivator implements BundleActivator {

	private static BundleContext context;
	
	private ServiceReference<?> friendReference;
	private FriendsManager friendManager;
	ServiceRegistration<?> registration;

	static BundleContext getContext() {
		return context;
	}

	public void start(BundleContext bundleContext) throws Exception {
		ServiceActivator.context = bundleContext;
		
		friendReference = context.getServiceReference(FriendsManager.class.getName());
		friendManager = (FriendsManager) context.getService(friendReference);
		
		System.out.println("Friends Consumer Started!");
		
		//register friend consumer
		FollowerConsumer consumer = new FollowerConsumer(friendManager);
		registration = context.registerService(
				FollowerConsumer.class.getName(), 
				consumer, 
				null);
		
		//consumer.displayMenu();
	}

	public void stop(BundleContext bundleContext) throws Exception {
		ServiceActivator.context = null;
		
		bundleContext.ungetService(friendReference);
		System.out.println("Friends Consumer Stopped!");
	}

}
