package com.redstream.postserviceconsumer;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import com.redstream.postmanagerservice.PostManager;
import com.redstream.usermanagerservice.IUser;

public class Activator implements BundleActivator {

	private static BundleContext context;
	private ServiceReference<?> postReference;
	private ServiceReference<?> userReference;

	static BundleContext getContext() {
		return context;
	}

	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		System.out.println("Post Consumer starting...");
		//find the post manager service
		postReference = context.getServiceReference(PostManager.class.getName());
		PostManager postManager = (PostManager) context.getService(postReference);
		
		//get user service
		userReference = context.getServiceReference(IUser.class.getName());
		IUser user = (IUser) context.getService(userReference);
		
		//create consumer
		Consumer currentConsumer = new Consumer(user, postManager);
		
		//start consumer activity
		currentConsumer.startMenu();
	}

	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
	}

}
