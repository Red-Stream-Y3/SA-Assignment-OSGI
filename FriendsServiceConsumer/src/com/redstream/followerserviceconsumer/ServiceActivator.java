package com.redstream.followerserviceconsumer;

import com.redstream.followerservicemanager.FriendsManager;
import com.redstream.usermanagerservice.IUser;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

public class ServiceActivator implements BundleActivator {


	private static BundleContext context; 
	
	// Declare service reference
	private ServiceReference<?> friendsReference;
	private ServiceReference<?> userReference;
	ServiceRegistration<?> registration;

	static BundleContext getContext() {
		return context;
	}

	public void start(BundleContext bundleContext) throws Exception {
		ServiceActivator.context = bundleContext;
		System.out.println("LOG - Friends Consumer Started!");

		
		friendsReference = context.getServiceReference(FriendsManager.class.getName());
		FriendsManager friendManager = (FriendsManager) context.getService(friendsReference);
		
		userReference = context.getServiceReference(IUser.class.getName());
		IUser user = (IUser) context.getService(userReference);

		// create consumer
		FollowerConsumer currentConsumer = new FollowerConsumer(user, friendManager);
		registration = context.registerService(
				FollowerConsumer.class.getName(), 
				currentConsumer, 
				null);

		// start consumer activity
		//currentConsumer.startMenu();
	}

	public void stop(BundleContext bundleContext) throws Exception {
		ServiceActivator.context = null;
		System.out.println("LOG - Friends Consumer Stopped!");
	}

}