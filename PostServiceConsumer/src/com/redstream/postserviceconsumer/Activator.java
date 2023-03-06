package com.redstream.postserviceconsumer;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import com.redstream.postmanagerservice.PostManager;

public class Activator implements BundleActivator {

	private static BundleContext context;
	private ServiceReference<?> postReference;

	static BundleContext getContext() {
		return context;
	}

	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		
		//find the post manager service
		postReference = context.getServiceReference(PostManager.class.getName());
		PostManager postManager = (PostManager) context.getService(postReference);
		
		//call the new post method from PostManager
		postManager.newPost();
	}

	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
	}

}
