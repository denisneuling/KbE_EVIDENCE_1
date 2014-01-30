package de.htw_berlin.aStudent.dao;

import java.util.Date;
import java.util.List;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.mysema.query.jpa.hibernate.HibernateQuery;

import de.htw_berlin.aStudent.dao.api.AbstractDao;
import de.htw_berlin.aStudent.dao.api.qry.SelectQuery;
import de.htw_berlin.aStudent.model.PMessage;
import de.htw_berlin.aStudent.model.QPMessage;
import de.htw_berlin.aStudent.model.QPTopic;

/**
 * <p>PMessageDao class.</p>
 *
 * @author Denis Neuling (denisneuling@gmail.com)
 * 
 */
@Repository
public class PMessageDao extends AbstractDao<PMessage, Long> {

	/**
	 * <p>
	 * Constructor for PMessageDao.
	 * </p>
	 * 
	 * @param sessionFactory
	 *            a {@link org.hibernate.SessionFactory} object.
	 */
	@Autowired
	public PMessageDao(SessionFactory sessionFactory) {
		super(sessionFactory, PMessage.class);
	}

	public List<PMessage> findMessagesByTopic(final String topic) {
		return new SelectQuery<List<PMessage>>(getSession()) {
			@Override
			public List<PMessage> perform(HibernateQuery query) {
				QPMessage qpMessage = QPMessage.pMessage;
				return query.from(qpMessage)
							.join(qpMessage.topic)
							.where(
								qpMessage.topic.name.eq(topic)
							)
							.orderBy(qpMessage.date.desc())
							.list(qpMessage);
			}
		}.execute();
	}

	public List<PMessage> findMessagesByTopicSince(final String topic, final Date since) {
		return new SelectQuery<List<PMessage>>(getSession()) {
			@Override
			public List<PMessage> perform(HibernateQuery query) {
				QPMessage qpMessage = QPMessage.pMessage;
				return query.from(qpMessage)
							.join(qpMessage.topic)
							.where(
								qpMessage.topic.name.eq(topic)
								.and(qpMessage.date.after(since))
							)
							.orderBy(qpMessage.date.desc())
							.list(qpMessage);
			}
		}.execute();
	}
}
