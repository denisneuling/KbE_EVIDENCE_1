package de.htw_berlin.aStudent.map;

import org.dozer.CustomConverter;
import org.dozer.MappingException;

import de.htw_berlin.aStudent.model.PTopic;

public class TopicMapper implements CustomConverter {

	@Override
	public Object convert(Object destination, Object source, Class<?> destClass, Class<?> sourceClass) {
		if (source == null) {
			return null;
		}

		if (source instanceof PTopic) {
			return ((PTopic)source).getName();
		} else if (source instanceof String) {
			PTopic pTopic = new PTopic();
			pTopic.setName((String)source);
			return pTopic;
		} else {
			throw new MappingException("TopicMapper used incorrectly. Arguments passed in were:" + destClass.getName() + " and " + sourceClass.getName());
		}
	}

}
