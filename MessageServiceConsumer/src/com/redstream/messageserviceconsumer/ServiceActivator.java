package com.redstream.messageserviceconsumer;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import com.redstream.messagemanagerservice.IMessage;

public class ServiceActivator implements BundleActivator {

	// Declare service reference
	private ServiceReference<?> messageServiceReference;

	private IMessage messageService;

	// Start method
	@Override
	public void start(BundleContext bundleContext) throws Exception {
		messageServiceReference = bundleContext.getServiceReference(IMessage.class.getName());
		messageService = (IMessage) bundleContext.getService(messageServiceReference);

		System.out.println("Message Subscriber Started");

		messageService.sendMessage();
	}

	// Stop method
	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		bundleContext.ungetService(messageServiceReference);

		System.out.println("Message Subscriber Stopped");
	}

}