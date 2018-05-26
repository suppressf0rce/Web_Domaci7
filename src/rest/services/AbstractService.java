package rest.services;

import java.util.List;

import rest.dao.AbstractDAO;
import rest.entities.BaseEntity;

public abstract class AbstractService<T extends BaseEntity, DAO extends AbstractDAO<T>> implements IAbstractService<T> {
	public AbstractService(DAO dao) {
		this.dao = dao;
	}
	@Override
	public boolean add(T object) {
		return this.dao.add(object);
	}

	@Override
	public boolean removeById(int id) {
		return this.dao.removeById(id);
	}

	@Override
	public boolean update(T object) {
		return this.dao.update(object);
	}

	@Override
	public List<T> getAll() {
		return this.dao.getAll();
	}

	@Override
	public T getById(int id) {
		return this.dao.getById(id);
	}

	protected DAO dao; 
}

