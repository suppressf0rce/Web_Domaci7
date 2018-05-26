package rest.dao;

import java.util.List;

import rest.entities.Recept;

public interface ReceptDAO extends AbstractDAO<Recept>{
	
	List<Recept> searchByName(String name);
	List<Recept> range100(int range);

}
