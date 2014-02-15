package de.htw_berlin.aStudent.map;

import org.dozer.CustomConverter;
import org.dozer.MappingException;

import de.htw_berlin.aStudent.model.PMessage;
import de.htw_berlin.f4.ai.kbe.kurznachrichten.Message;
import de.htw_berlin.f4.ai.kbe.kurznachrichten.User;

public class MessageMapper implements CustomConverter {

	@Override
	public Object convert(Object destination, Object source, Class<?> destClass, Class<?> sourceClass) {
		if (source == null) {
			return null;
		}

		if (source instanceof PMessage) {
			PMessage m = (PMessage) source;
			Message message = new Message();
			message.setMessageId(m.getId());
			message.setContent(m.getContent());
			message.setDate(m.getDate());

			if (m.getTopic() != null)
				message.setTopic(m.getTopic().getName());

			message.setOrigin(m.isOrigin());

			if (m.getUser() != null) {

				User u = new User();
				u.setCity(m.getUser().getCity());
				u.setName(m.getUser().getName());

				message.setUser(u);
			}

			return message;
		}

		throw new MappingException("MessageMapper used incorrectly. Arguments passed in were:" + destClass.getName() + " and " + sourceClass.getName());
	}

}
