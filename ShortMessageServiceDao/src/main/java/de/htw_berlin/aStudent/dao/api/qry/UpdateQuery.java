package de.htw_berlin.aStudent.dao.api.qry;

import org.hibernate.Session;

import com.mysema.query.jpa.hibernate.HibernateUpdateClause;
import com.mysema.query.types.EntityPath;

/**
 * <p>
 * Abstract UpdateQuery class.
 * </p>
 * 
 * @author Denis Neuling (denisneuling@gmail.com)
 * 
 */
public abstract class UpdateQuery {
	private Session session;
	private EntityPath<?> entity;
	private boolean closeIfNeeded = false;

	/**
	 * <p>
	 * Constructor for UpdateQuery.
	 * </p>
	 * 
	 * @param session
	 *            a {@link org.hibernate.Session} object.
	 * @param entity
	 *            a {@link com.mysema.query.types.EntityPath} object.
	 */
	public UpdateQuery(Session session, EntityPath<?> entity) {
		this.session = session;
		this.entity = entity;
	}

	/**
	 * <p>
	 * Constructor for UpdateQuery.
	 * </p>
	 * 
	 * @param session
	 *            a {@link org.hibernate.Session} object.
	 * @param entity
	 *            a {@link com.mysema.query.types.EntityPath} object.
	 * @param closeIfNeeded
	 *            a boolean.
	 */
	public UpdateQuery(Session session, EntityPath<?> entity, boolean closeIfNeeded) {
		this.session = session;
		this.entity = entity;
		this.closeIfNeeded = closeIfNeeded;
	}

	/**
	 * <p>
	 * perform.
	 * </p>
	 * 
	 * @param updateClause
	 *            a {@link com.mysema.query.jpa.hibernate.HibernateUpdateClause}
	 *            object.
	 */
	public abstract void perform(HibernateUpdateClause updateClause);

	/**
	 * <p>
	 * execute.
	 * </p>
	 */
	public final void execute() {
		HibernateUpdateClause updateClause = new HibernateUpdateClause(session, entity);
		try {
			perform(updateClause);
		} finally {
			if (closeIfNeeded) {
				session.close();
			}
		}
	}
}