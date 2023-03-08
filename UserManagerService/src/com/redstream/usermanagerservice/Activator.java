package com.redstream.usermanagerservice;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class Activator implements BundleActivator {

	private static BundleContext context;

	private ServiceRegistration<?> serviceRegistration;

	static BundleContext getContext() {
		return context;
	}

	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		System.out.println("User Manager Service Started");
		IUser user = new UserImpl();
		serviceRegistration = bundleContext.registerService(IUser.class.getName(),user, null);
	}

	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
		System.out.println("User Manager Service Stopped");
		serviceRegistration.unregister();
	}
	

}
