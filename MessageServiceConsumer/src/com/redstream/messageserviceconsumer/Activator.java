package com.redstream.messageserviceconsumer;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import com.redstream.messagemanagerservice.IMessage;
import com.redstream.usermanagerservice.IUser;

public class Activator implements BundleActivator {

	private static BundleContext context; 
	
	// Declare service reference
	private ServiceReference<?> messageReference;
	private ServiceReference<?> userReference;

	static BundleContext getContext() {
		return context;
	}

	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		System.out.println("LOG - Message Consumer Started!");

		
		messageReference = context.getServiceReference(IMessage.class.getName());
		IMessage postManager = (IMessage) context.getService(messageReference);

		userReference = context.getServiceReference(IUser.class.getName());
		IUser user = (IUser) context.getService(userReference);

		// create consumer
		Consumer currentConsumer = new Consumer(user, postManager);

		// start consumer activity
		currentConsumer.startMenu();
	}

	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
		System.out.println("LOG - Message Consumer Stopped!");
	}

}