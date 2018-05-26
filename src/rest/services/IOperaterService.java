package rest.services;

import rest.entities.Operater;

public interface IOperaterService extends IAbstractService<Operater>{

	Operater getOperater(String username, String password);
	
}
