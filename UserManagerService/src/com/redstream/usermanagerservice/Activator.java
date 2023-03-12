package com.redstream.usermanagerservice;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class Activator implements BundleActivator {

	private static BundleContext context;

	private ServiceRegistration<?> serviceRegistration; // Service registration object to register the service with OSGi framework

	static BundleContext getContext() {
		return context;
	}

	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext; 
		System.out.println("LOG - User Manager Service Started");
		IUser user = new UserImpl();
		serviceRegistration = bundleContext.registerService(IUser.class.getName(),user, null); // Register the service with OSGi framework
	}

	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
		System.out.println("LOG - User Manager Service Stopped"); 
		serviceRegistration.unregister(); // Unregister the service from OSGi framework
	}
	

}
