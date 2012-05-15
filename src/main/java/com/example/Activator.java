package com.example;


import org.osgi.framework.BundleActivator;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import com.change_vision.jude.api.inf.ui.IMessageDialogHandler;
import com.change_vision.jude.api.inf.ui.IMessageDialogHandlerFactory;

public class Activator implements BundleActivator {
	private static IMessageDialogHandler messageHandler;
	private ServiceReference reference;
	
	public void start(BundleContext context) {
		initializeMessageDialogHandler(context);
	}

	public void stop(BundleContext context) {
	}
	
	private void initializeMessageDialogHandler(BundleContext context) {
		reference = context.getServiceReference(IMessageDialogHandlerFactory.class.getName());
		IMessageDialogHandlerFactory factory = (IMessageDialogHandlerFactory)context.getService(reference);
		if(factory != null) {
			messageHandler = factory.createMessageDialogHandler(new Messages(), "\\.astah\\professional|uml|community\\tutorial_sample.log");
		}
		context.ungetService(reference);
	}
	
	public static IMessageDialogHandler getMessageHandler() {
		return messageHandler;
	}
	
}

