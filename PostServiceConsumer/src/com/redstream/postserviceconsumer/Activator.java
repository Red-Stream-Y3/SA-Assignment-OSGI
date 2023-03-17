package com.redstream.postserviceconsumer;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.framework.ServiceRegistration;

import com.redstream.postmanagerservice.PostManager;
import com.redstream.usermanagerservice.IUser;

public class Activator implements BundleActivator {

	private static BundleContext context;
	private ServiceReference<?> postReference;
	private ServiceReference<?> userReference;
	ServiceRegistration<?> serviceRegistration;

	static BundleContext getContext() {
		return context;
	}

	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		
		//find the post manager service
		postReference = context.getServiceReference(PostManager.class.getName());
		PostManager postManager = (PostManager) context.getService(postReference);
		
		//get user service
		userReference = context.getServiceReference(IUser.class.getName());
		IUser user = (IUser) context.getService(userReference);
		
		//create consumer
		PostConsumer currentConsumer = new PostConsumer(user, postManager);
		
		//register consumer
		serviceRegistration = context.registerService(
				PostConsumer.class.getName(), 
				currentConsumer, 
				null);
		
		//start consumer activity
		//currentConsumer.startMenu();
	}

	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
	}

}
