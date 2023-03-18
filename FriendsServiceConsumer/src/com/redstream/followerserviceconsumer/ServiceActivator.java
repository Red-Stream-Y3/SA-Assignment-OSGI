package com.redstream.followerserviceconsumer;

import com.redstream.followerservicemanager.FriendsManager;
import com.redstream.usermanagerservice.IUser;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

public class ServiceActivator implements BundleActivator {

	private static BundleContext context;
	
	private ServiceReference<?> friendReference;
	private ServiceReference<?> userReference;
	
	private FriendsManager friendManager;

	static BundleContext getContext() {
		return context;
	}

	public void start(BundleContext bundleContext) throws Exception {
		ServiceActivator.context = bundleContext;
		
		friendReference = context.getServiceReference(FriendsManager.class.getName());
		friendManager = (FriendsManager) context.getService(friendReference);
		
		userReference = context.getServiceReference(IUser.class.getName());
		IUser user = (IUser) context.getService(userReference);
		
		System.out.println("Friends Consumer Started!");
		
		FollowerConsumer currentConsumer = new FollowerConsumer(friendManager, user);
		
		
		//start consumer
		currentConsumer.displayMenu();
		
	}

	public void stop(BundleContext bundleContext) throws Exception {
		ServiceActivator.context = null;
		bundleContext.ungetService(friendReference);
		System.out.println("Friends Consumer Stopped!");
	}

}
