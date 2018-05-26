package rest;

import java.util.HashSet;
import java.util.Set;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;

import rest.controllers.OperaterController;
import rest.controllers.ReceptController;

@ApplicationPath("/rest")
public class DomaciRest extends Application{
	

	@Override
	public Set<Class<?>> getClasses() {
		Set<Class<?>> classes = new HashSet<>();
		classes.add(ReceptController.class);
		classes.add(OperaterController.class);
		return classes;
	}
	

}
