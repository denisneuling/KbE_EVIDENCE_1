package de.htw_berlin.aStudent.dao.api.qry;

import org.hibernate.Session;

import com.mysema.query.jpa.hibernate.HibernateQuery;

/**
 * <p>
 * Abstract SelectQuery class.
 * </p>
 * 
 * @author Denis Neuling (denisneuling@gmail.com)
 * 
 */
public abstract class SelectQuery<T> {
	private Session session;
	private boolean closeIfNeeded = false;

	/**
	 * <p>
	 * Constructor for SelectQuery.
	 * </p>
	 * 
	 * @param session
	 *            a {@link org.hibernate.Session} object.
	 */
	public SelectQuery(Session session) {
		this.session = session;
	}

	/**
	 * <p>
	 * Constructor for SelectQuery.
	 * </p>
	 * 
	 * @param session
	 *            a {@link org.hibernate.Session} object.
	 * @param closeIfNeeded
	 *            a boolean.
	 */
	public SelectQuery(Session session, boolean closeIfNeeded) {
		this.session = session;
		this.closeIfNeeded = closeIfNeeded;
	}

	/**
	 * <p>
	 * perform.
	 * </p>
	 * 
	 * @param query
	 *            a {@link com.mysema.query.jpa.hibernate.HibernateQuery}
	 *            object.
	 * @return a T object.
	 */
	public abstract T perform(HibernateQuery query);

	/**
	 * <p>
	 * execute.
	 * </p>
	 * 
	 * @return a T object.
	 */
	public final T execute() {
		HibernateQuery query = new HibernateQuery(session);
		try {
			return perform(query);
		} finally {
			if (closeIfNeeded) {
				session.close();
			}
		}
	}
}
