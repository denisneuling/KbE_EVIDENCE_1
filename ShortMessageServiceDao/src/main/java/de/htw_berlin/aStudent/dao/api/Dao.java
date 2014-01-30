package de.htw_berlin.aStudent.dao.api;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * The generic interface for create-, update-, and delete- operations onto ORM
 * side
 * 
 * @author Denis Neuling (denisneuling@gmail.com)
 * 
 */
public interface Dao<T, ID extends Serializable> {

	/**
	 * <p>
	 * validate.
	 * </p>
	 * 
	 * @param entity
	 *            a T object.
	 */
	public void validate(T entity);

	/**
	 * <p>
	 * refresh.
	 * </p>
	 * 
	 * @param entity
	 *            a T object.
	 */
	public void refresh(T entity);

	/**
	 * Deletes the serializable entity with type t from the table which is
	 * associated with the concerning type
	 * 
	 * @author Denis Neuling <denisneuling@gmail.com>
	 * @param entity
	 *            a T object.
	 */
	public void delete(T entity);

	/**
	 * Counts size of table
	 * 
	 * @return size
	 */
	public Long count();

	/**
	 * Retrieves all serializable entities with type t from the table which is
	 * associated with the concerning type
	 * 
	 * @author Denis Neuling <denisneuling@gmail.com>
	 * @return ts all retrievable typed entities with type t
	 */
	public List<T> findAll();

	public PagedListHolder<T> findIn(Pagination pagination);
	
	/**
	 * <p>
	 * findById.
	 * </p>
	 * 
	 * @param id
	 *            a ID object.
	 * @return a T object.
	 */
	public T findById(ID id);

	/**
	 * Saves the serializable entity with type t at the table which is
	 * associated with the concerning type
	 * 
	 * @author Denis Neuling <denisneuling@gmail.com>
	 * @return id the serializable id of the just inserted entity
	 * @param entity
	 *            a T object.
	 */
	public ID save(T entity);

	/**
	 * Saves a collection of entites
	 * 
	 * @author Denis Neuling <denisneuling@gmail.com>
	 * @param entities
	 *            a {@link java.util.Collection} object.
	 */
	public void saveAll(Collection<T> entities);

	/**
	 * Saves a collection of entites
	 * 
	 * @author Denis Neuling <denisneuling@gmail.com>
	 * @param entities
	 *            a T object.
	 */
	public void saveAll(T... entities);

	/**
	 * Updates the serializable entity with type t at the table which is
	 * associated with the concerning type
	 * 
	 * @author Denis Neuling <denisneuling@gmail.com>
	 * @param entity
	 *            a T object.
	 */
	public void update(T entity);

	/**
	 * Updates all given entities
	 * 
	 * @author Denis Neuling <denisneuling@gmail.com>
	 * @param entities
	 *            a {@link java.util.Collection} object.
	 */
	public void updateAll(Collection<T> entities);

	/**
	 * Updates all given entities
	 * 
	 * @author Denis Neuling <denisneuling@gmail.com>
	 * @param entities
	 *            a T object.
	 */
	public void updateAll(T... entities);
}

