package de.htw_berlin.aStudent.dao.api.qry;

import org.hibernate.Session;

import com.mysema.query.jpa.hibernate.HibernateDeleteClause;
import com.mysema.query.types.EntityPath;

/**
 * <p>
 * Abstract DeleteQuery class.
 * </p>
 * 
 * @author Denis Neuling (denisneuling@gmail.com)
 * 
 */
public abstract class DeleteQuery {
	private Session session;
	private EntityPath<?> entity;
	private boolean closeIfNeeded = false;

	/**
	 * <p>
	 * Constructor for DeleteQuery.
	 * </p>
	 * 
	 * @param session
	 *            a {@link org.hibernate.Session} object.
	 * @param entity
	 *            a {@link com.mysema.query.types.EntityPath} object.
	 */
	public DeleteQuery(Session session, EntityPath<?> entity) {
		this.session = session;
		this.entity = entity;
	}

	/**
	 * <p>
	 * Constructor for DeleteQuery.
	 * </p>
	 * 
	 * @param session
	 *            a {@link org.hibernate.Session} object.
	 * @param entity
	 *            a {@link com.mysema.query.types.EntityPath} object.
	 * @param closeIfNeeded
	 *            a boolean.
	 */
	public DeleteQuery(Session session, EntityPath<?> entity, boolean closeIfNeeded) {
		this.session = session;
		this.entity = entity;
		this.closeIfNeeded = closeIfNeeded;
	}

	/**
	 * <p>
	 * perform.
	 * </p>
	 * 
	 * @param deleteClause
	 *            a {@link com.mysema.query.jpa.hibernate.HibernateDeleteClause}
	 *            object.
	 */
	public abstract void perform(HibernateDeleteClause deleteClause);

	/**
	 * <p>
	 * execute.
	 * </p>
	 */
	public final void execute() {
		HibernateDeleteClause deleteClause = new HibernateDeleteClause(session, entity);
		try {
			perform(deleteClause);
		} finally {
			if (closeIfNeeded) {
				session.close();
			}
		}
	}
}

