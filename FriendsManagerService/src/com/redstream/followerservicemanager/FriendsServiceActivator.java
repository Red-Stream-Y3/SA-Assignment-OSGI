package com.redstream.followerservicemanager;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class FriendsServiceActivator implements BundleActivator {

	private static BundleContext context;
	
	private ServiceRegistration<?> serviceRegistration;

	static BundleContext getContext() {
		return context;
	}

	public void start(BundleContext bundleContext) throws Exception {
		FriendsServiceActivator.context = bundleContext;
		
		System.out.println("Friends Managing Producer Started!");
		FriendsManager friendManager = new FriendsManagerImpl();
		serviceRegistration = bundleContext.registerService(FriendsManager.class.getName(), friendManager, null);
	}

	public void stop(BundleContext bundleContext) throws Exception {
		FriendsServiceActivator.context = null;
		
		System.out.println("Friends Managing Producer Stopped!");
		serviceRegistration.unregister();
	}

}
