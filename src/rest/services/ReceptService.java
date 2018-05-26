package rest.services;

import java.util.List;

import rest.dao.ReceptDAO;
import rest.entities.Recept;

public class ReceptService extends AbstractService<Recept, ReceptDAO> implements IReceptService {

	public ReceptService(ReceptDAO dao) {
		super(dao);
		// TODO Auto-generated constructor stub
	}

	@Override
	public List<Recept> searchByName(String name) {
		return dao.searchByName(name);
	}

	@Override
	public List<Recept> range100(int range) {
		// TODO Auto-generated method stub
		return dao.range100(range);
	}

}
