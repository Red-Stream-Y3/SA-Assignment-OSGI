package com.redstream.postmanagerservice;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class Activator implements BundleActivator {

	private static BundleContext context;
	ServiceRegistration<?> serviceRegistration;

	static BundleContext getContext() {
		return context;
	}

	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		
		//register the post manager service
		PostManager postManager = new PostManagerImpl();
		serviceRegistration = bundleContext.registerService(
				PostManager.class.getName(), 
				postManager, 
				null);
	}

	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
		System.out.println("Post Manager Stopped!");
		serviceRegistration.unregister();
	}

}
