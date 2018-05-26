package rest.dao;

import java.util.List;

import rest.entities.BaseEntity;

public interface AbstractDAO<T extends BaseEntity> {

	boolean add(T object);
	boolean removeById(int id);
	boolean update(T object);
	List<T> getAll();
	T getById(int id);
	
}
