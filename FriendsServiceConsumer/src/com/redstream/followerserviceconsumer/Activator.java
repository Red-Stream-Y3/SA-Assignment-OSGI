package com.redstream.followerserviceconsumer;

import com.redstream.followerservicemanager.FriendsManager;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

public class Activator implements BundleActivator {

	private static BundleContext context;
	
	private ServiceReference<?> friendReference;

	static BundleContext getContext() {
		return context;
	}

	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		
		friendReference = context.getServiceReference(FriendsManager.class.getName());
		FriendsManager friendManager = (FriendsManager) context.getService(friendReference);
		
		System.out.println("Friends Managing Consumer Started!");
		
		friendManager.addFriend();
	}

	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
		
		bundleContext.ungetService(friendReference);
		System.out.println("Friends Managing Consumer Stopped!");
	}

}
