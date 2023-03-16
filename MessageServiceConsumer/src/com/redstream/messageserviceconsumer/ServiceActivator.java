package com.redstream.messageserviceconsumer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

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

		System.out.println("LOG - Message Consumer Started!");

		displayMessage(messageService);

	}

	private void displayMessage(IMessage message) throws IOException {

		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));

		String option = "";

		try {
			inputLoop: while (true) {
				System.out.println("\n===================");
				System.out.println(" Message Dashboard");
				System.out.println("===================");
				System.out.println("1. Send Message");
				System.out.println("2. Delete Message");
				System.out.println("3. Search Message");
				System.out.println("4. View All Messages");
				System.out.println("5. Exit");
				System.out.print("Enter your choice: ");

				option = reader.readLine();

				switch (option) {
				case "1": {
					message.sendMessage();
					break;
				}
				case "2": {
					message.deleteMessage();
					break;
				}
				case "3": {
					message.searchMessages();
					break;
				}
				case "4": {
					message.viewAllMessages();
					break;
				}
				case "5": {
					System.out.println("Terminating Program!\n");
					System.exit(0);
				}
				default: {
					System.out.println("Unexpected value: Enter 1/2/3 or leave empty to cancel\n");
					continue inputLoop;
				}
				}
			}
		} catch (Exception e) {
			// Print error message if exception occurs
			System.out.println(e.getMessage());
		}

	}

	// Stop method
	@Override
	public void stop(BundleContext bundleContext) throws Exception {
		bundleContext.ungetService(messageServiceReference);

		System.out.println("Message Consumer Stopped!");
	}

}