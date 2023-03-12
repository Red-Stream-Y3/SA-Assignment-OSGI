package com.redstream.messagemanagerservice;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class Activator implements BundleActivator {
	private static BundleContext context;

	// Declare service registration
	private ServiceRegistration<?> serviceRegistration;

	static BundleContext getContext() {
		return context;
	}

	// Start method
	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		System.out.println("Message Producer Started!");
		serviceRegistration = bundleContext.registerService(IMessage.class.getName(), new MessageImpl(), null);
	}

	// Stop method
	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		System.out.println("Message Producer Stopped!");
		serviceRegistration.unregister();
	}

}
