package de.htw_berlin.aStudent.dao.api;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.validation.ConstraintViolation;

import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import com.mysema.query.jpa.hibernate.HibernateQuery;
import com.mysema.query.types.path.EntityPathBase;

import de.htw_berlin.aStudent.model.api.AbstractModel;
import de.htw_berlin.aStudent.dao.api.exeption.ValidationException;
import de.htw_berlin.aStudent.dao.api.qry.SelectQuery;

/**
 * The AbstractDao to get access to the serializable database entities
 * 
 * @author Denis Neuling (denisneuling@gmail.com)
 * @param <T>
 */
@Repository
public abstract class AbstractDao<T extends AbstractModel<T, ID>, ID extends Serializable> implements Dao<T, ID> {
	protected final Logger log = Logger.getLogger(getClass());

	@Autowired(required = false)
	protected LocalValidatorFactoryBean validator;

	private SessionFactory sessionFactory;

	protected Class<T> clazz;

	/**
	 * <p>
	 * Setter for the field <code>sessionFactory</code>.
	 * </p>
	 * 
	 * @param sessionFactory
	 *            a {@link org.hibernate.SessionFactory} object.
	 */
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	/**
	 * <p>
	 * Constructor for AbstractDao.
	 * </p>
	 * 
	 * @param sessionFactory
	 *            a {@link org.hibernate.SessionFactory} object.
	 * @param clazz
	 *            a {@link java.lang.Class} object.
	 */
	public AbstractDao(SessionFactory sessionFactory, Class<T> clazz) {
		this.setSessionFactory(sessionFactory);
		this.clazz = clazz;
	}

	/**
	 * <p>
	 * getSession.
	 * </p>
	 * 
	 * @return a {@link org.hibernate.Session} object.
	 */
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}

	/** {@inheritDoc} */
	public Long count() {
		return new SelectQuery<Long>(getSession()) {
			@Override
			public Long perform(HibernateQuery query) {
				EntityPathBase<T> qEntity = new EntityPathBase<T>(clazz, clazz.getSimpleName());
				return query.from(qEntity).count();
			}
		}.execute();
	}

	/** {@inheritDoc} */
	public PagedListHolder<T> findIn(Pagination pagination) {
		final int page = pagination.getPage();
		final int size = pagination.getSize();

		log.debug("Loading " + size + " entries of " + this.clazz.getName() + " of page " + page);

		PagedListHolder<T> pagedListHolder = new PagedListHolder<T>(new SelectQuery<List<T>>(getSession()) {
			@Override
			public List<T> perform(HibernateQuery query) {
				EntityPathBase<T> qEntity = new EntityPathBase<T>(clazz, clazz.getSimpleName());
				return query.from(qEntity).offset(page * size).limit(size).list(qEntity);
			}
		}.execute());
		pagedListHolder.setPageSize(pagedListHolder.getResult().size());
		pagedListHolder.setPage(pagination.getPage());
		
		long count = this.count();
		pagedListHolder.setMaxLinkedPages( count%size==0 ? ((int) (count / size)) : ((int) (count / size) +1) );
		return pagedListHolder;
	}

	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	public T findById(ID id) {
		return (T) this.getSession().get(clazz, (Serializable) id);
	}

	/** {@inheritDoc} */
	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		log.debug("Loading all Rows of " + this.clazz.getClass());

		return (List<T>) this.getSession().createCriteria(this.clazz).list();
	}

	/** {@inheritDoc} */
	public void delete(T entity) {
		log.debug("Deleting " + entity.getClass());

		this.getSession().delete(entity);
	}

	/** {@inheritDoc} */
	public void refresh(T entity) {
		this.getSession().refresh(entity);
	}

	/** {@inheritDoc} */
	public ID save(T entity) {
		log.debug("Saving " + entity.getClass());

		this.validate(entity);
		Serializable id = this.getSession().save(entity);
		return (ID) id;
	}

	/** {@inheritDoc} */
	public void saveAll(Collection<T> ts) {
		log.debug("Saving Collection of " + this.clazz.getClass());

		for (T entity : ts) {
			this.save(entity);
		}
	}

	/** {@inheritDoc} */
	public void saveAll(T... entities) {
		log.debug("Saving Collection of untyped Entities");

		for (T entity : entities) {
			this.save(entity);
		}
	}

	/** {@inheritDoc} */
	public void update(T entity) {
		log.debug("Updating " + entity.getClass());

		this.validate(entity);
		this.getSession().update(entity);
	}

	/** {@inheritDoc} */
	public void updateAll(Collection<T> entities) {
		log.debug("Saving Collection of " + this.clazz.getClass());

		for (T entity : entities) {
			this.validate(entity);
			this.update(entity);
		}
	}

	/** {@inheritDoc} */
	public void updateAll(T... entities) {
		log.debug("Saving Collection of " + this.clazz.getClass());

		for (T entity : entities) {
			this.validate(entity);
			this.update(entity);
		}
	}

	/** {@inheritDoc} */
	public void validate(T entity) {
		if (validator != null) {
			log.debug("Validating " + entity.getClass());

			Set<ConstraintViolation<T>> constraintViolations = this.validator.validate(entity);
			if (constraintViolations.size() > 0) {
				throw new ValidationException(constraintViolations);
			}
		}
	}
}

