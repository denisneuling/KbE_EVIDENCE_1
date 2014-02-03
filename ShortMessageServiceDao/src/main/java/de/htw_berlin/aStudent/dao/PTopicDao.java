package de.htw_berlin.aStudent.dao;

import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mysema.query.jpa.hibernate.HibernateQuery;

import de.htw_berlin.aStudent.dao.api.AbstractDao;
import de.htw_berlin.aStudent.dao.api.qry.SelectQuery;
import de.htw_berlin.aStudent.model.PTopic;
import de.htw_berlin.aStudent.model.QPTopic;

/**
 * <p>PTopicDao class.</p>
 *
 * @author Denis Neuling (denisneuling@gmail.com)
 * 
 */
@Repository
public class PTopicDao extends AbstractDao<PTopic, Long> {

	/**
	 * <p>
	 * Constructor for PTopicDao.
	 * </p>
	 * 
	 * @param sessionFactory
	 *            a {@link org.hibernate.SessionFactory} object.
	 */
	@Autowired
	public PTopicDao(SessionFactory sessionFactory) {
		super(sessionFactory, PTopic.class);
	}
	
	public List<PTopic> all() {
		List<PTopic> all = new SelectQuery<List<PTopic>>(getSession()) {
			@Override
			public List<PTopic> perform(HibernateQuery query) {
				QPTopic qpTopic = QPTopic.pTopic;
				return query.from(qpTopic).list(qpTopic);
			}
		}.execute();
		return all;
	}
	
	public PTopic findByName(final String name){
		return new SelectQuery<PTopic>(getSession()) {
			@Override
			public PTopic perform(HibernateQuery query) {
				QPTopic qpTopic = QPTopic.pTopic;
				return query.from(qpTopic).where(qpTopic.name.eq(name)).uniqueResult(qpTopic);
			}
		}.execute();
	}
}
