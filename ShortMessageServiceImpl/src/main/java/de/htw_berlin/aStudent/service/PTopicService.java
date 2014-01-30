package de.htw_berlin.aStudent.service;

import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import de.htw_berlin.aStudent.model.PTopic;

@Transactional
public interface PTopicService {

	public void createTopic(String userName, String topic);

	public List<PTopic> all();
	
	public PTopic findByName(String name);
}