package com.redstream.userserviceconsumer;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

import com.redstream.usermanagerservice.IUser;

public class Activator implements BundleActivator {

	private static BundleContext context;
	private ServiceReference<?> userServiceReference;
	ServiceRegistration<?> serviceRegistration;
	
	static BundleContext getContext() { // Get bundle context
		return context;
	}

	@Override
	public void start(BundleContext bundleContext) throws Exception { // Start bundle
		Activator.context = bundleContext;
		
		userServiceReference = bundleContext.getServiceReference(IUser.class.getName()); // Get service reference
		IUser userService = (IUser) bundleContext.getService(userServiceReference); // Get service object
		
		//register user login 
		UserLogin login = new UserLogin(userService);
		serviceRegistration = context.registerService(
				UserLogin.class.getName(), 
				login, 
				null);
		
		//login.showLoginCLI();
		
		//register user consumer
		UserConsumer consumer = new UserConsumer(userService);
		serviceRegistration = context.registerService(
				UserConsumer.class.getName(), 
				consumer, 
				null);

		//consumer.showUserManagerCLI();
	}

	@Override
	public void stop(BundleContext bundleContext) throws Exception { // Stop bundle
		Activator.context = null;
		bundleContext.ungetService(userServiceReference);
		System.out.println("LOG - User Subscriber Stopped");
	}

	
}