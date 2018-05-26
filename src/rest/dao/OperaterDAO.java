package rest.dao;

import rest.entities.Operater;

public interface OperaterDAO extends AbstractDAO<Operater>{

	Operater getOperater(String username, String password);
	
}
