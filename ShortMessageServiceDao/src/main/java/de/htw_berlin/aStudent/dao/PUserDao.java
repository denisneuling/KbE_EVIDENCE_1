package de.htw_berlin.aStudent.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mysema.query.jpa.hibernate.HibernateQuery;

import de.htw_berlin.aStudent.dao.api.AbstractDao;
import de.htw_berlin.aStudent.dao.api.qry.SelectQuery;
import de.htw_berlin.aStudent.model.PUser;
import de.htw_berlin.aStudent.model.QPUser;

/**
 * <p>PUserDao class.</p>
 *
 * @author Denis Neuling (denisneuling@gmail.com)
 * 
 */
@Repository
public class PUserDao extends AbstractDao<PUser, Long> {

	/**
	 * <p>
	 * Constructor for PUserDao.
	 * </p>
	 * 
	 * @param sessionFactory
	 *            a {@link org.hibernate.SessionFactory} object.
	 */
	@Autowired
	public PUserDao(SessionFactory sessionFactory) {
		super(sessionFactory, PUser.class);
	}
	
	public List<PUser> all() {
		return new SelectQuery<List<PUser>>(getSession()) {
			@Override
			public List<PUser> perform(HibernateQuery query) {
				QPUser qpUser = QPUser.pUser;
				return query.from(qpUser).list(qpUser);
			}
		}.execute();
	}

	public PUser findByName(final String userName) {
		return new SelectQuery<PUser>(getSession()) {
			@Override
			public PUser perform(HibernateQuery query) {
				QPUser qpUser = QPUser.pUser;
				return query.from(qpUser).where(qpUser.name.eq(userName)).uniqueResult(qpUser);
			}
		}.execute();
	}
}
