package rest.services;

import java.util.List;

import rest.entities.Recept;

public interface IReceptService extends IAbstractService<Recept>{
	
	List<Recept> searchByName(String name);
	List<Recept> range100(int range);

}
