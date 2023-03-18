package com.redstream.mainmenu;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;

public class Activator implements BundleActivator {

	private static BundleContext context;

	static BundleContext getContext() {
		return context;
	}

	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		
		//create a menu navigator
		MenuNavigator navigator = new MenuNavigator(Activator.context);
		
		//start the main menu
		navigator.startMainMenu();
	}

	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
	}

}
