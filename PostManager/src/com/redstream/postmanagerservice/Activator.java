package com.redstream.postmanagerservice;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class Activator implements BundleActivator {

	private static BundleContext context;
	private ServiceRegistration<?> serviceRegistration;

	static BundleContext getContext() {
		return context;
	}

	public void start(BundleContext bundleContext) throws Exception {
		Activator.context = bundleContext;
		
		try {
			//create a database connection
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conn = DriverManager.getConnection(
					"jdbc:mysql://localhost:3306/socialmediadb","root","");
			
			System.out.println("Post Manager Connected to DB!");
			//register the post manager service
			PostManager postManager = new PostManagerImpl(conn);
			serviceRegistration = context.registerService(
					PostManager.class.getName(), 
					postManager, 
					null);
			System.out.println("Post Service Registered!");
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}

	public void stop(BundleContext bundleContext) throws Exception {
		Activator.context = null;
		System.out.println("Post Manager Stopped!");
		serviceRegistration.unregister();
	}

}
