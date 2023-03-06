package com.redstream.cameraprovider;

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
		
		//register the camera provider service
		CameraProvider cameraProvider = new CameraProviderImpl();
		serviceRegistration = bundleContext.registerService(
				CameraProvider.class.getName(), 
				cameraProvider, 
				null);
	}

	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
		System.out.println("Camera Provider Stopped!");
		serviceRegistration.unregister();
	}

}
