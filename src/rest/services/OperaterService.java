package rest.services;

import rest.dao.OperaterDAO;
import rest.entities.Operater;

public class OperaterService extends AbstractService<Operater, OperaterDAO> implements IOperaterService{

	public OperaterService(OperaterDAO dao) {
		super(dao);
	}

	@Override
	public Operater getOperater(String username, String password) {
		return dao.getOperater(username, password);
	}
	
}
