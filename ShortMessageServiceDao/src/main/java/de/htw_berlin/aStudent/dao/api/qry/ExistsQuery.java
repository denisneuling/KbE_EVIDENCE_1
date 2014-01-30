package de.htw_berlin.aStudent.dao.api.qry;

import org.hibernate.Session;

import com.mysema.query.jpa.hibernate.HibernateQuery;

/**
 * <p>
 * Abstract ExistsQuery class.
 * </p>
 * 
 * @author Denis Neuling (denisneuling@gmail.com)
 * 
 */
public abstract class ExistsQuery extends SelectQuery<Boolean> {

	/**
	 * <p>
	 * Constructor for ExistsQuery.
	 * </p>
	 * 
	 * @param session
	 *            a {@link org.hibernate.Session} object.
	 */
	public ExistsQuery(Session session) {
		super(session);
	}

	/**
	 * <p>
	 * Constructor for ExistsQuery.
	 * </p>
	 * 
	 * @param session
	 *            a {@link org.hibernate.Session} object.
	 * @param closeIfNeeded
	 *            a boolean.
	 */
	public ExistsQuery(Session session, boolean closeIfNeeded) {
		super(session, closeIfNeeded);
	}

	/** {@inheritDoc} */
	@Override
	public abstract Boolean perform(HibernateQuery query);
}
