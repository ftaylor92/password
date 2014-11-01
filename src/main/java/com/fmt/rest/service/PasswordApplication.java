package com.fmt.rest.service;



import java.util.HashSet;
import java.util.Set;
import javax.ws.rs.core.Application;

public class PasswordApplication extends Application {

	private Set<Object> svc_singletons = new HashSet<Object>();	
	private Set<Class<?>> svc_classes  = new HashSet<Class<?>>();

	public PasswordApplication() {
		svc_singletons.add(HelloWorldResource.class);
		svc_singletons.add(HelloResource.class);
		svc_singletons.add(PasswordStorage.class);
		svc_singletons.add(ContactResource.class);
		svc_singletons.add(WineResource.class);
		
		svc_classes.add(HelloWorldResource.class);
		svc_classes.add(HelloResource.class);
		svc_classes.add(PasswordStorage.class);
		svc_classes.add(ContactResource.class);
		svc_classes.add(WineResource.class);
	}
	
	@Override
	public Set<Object> getSingletons() {
		return svc_singletons;
	}
	 
	/*@Override
	public Set<Class<?>> getClasses() {
		return svc_classes;
	}*/
	
	@Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> classes = new HashSet<Class<?>>();
        classes.add(HelloWorldResource.class);
        classes.add(HelloResource.class);
        classes.add(PasswordStorage.class);
        classes.add(ContactResource.class);
        classes.add(WineResource.class);
        return classes;
    }
}