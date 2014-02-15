package de.htw_berlin.aStudent.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.htw_berlin.aStudent.dao.PTopicDao;
import de.htw_berlin.aStudent.model.PTopic;
import de.htw_berlin.aStudent.model.PUser;
import de.htw_berlin.aStudent.service.PTopicService;
import de.htw_berlin.aStudent.service.PUserService;

@Service
public class PTopicServiceImpl implements PTopicService {

	@Autowired
	private PTopicDao pTopicDao;

	@Autowired
	private PUserService pUserService;

	@Override
	public void createTopic(String userName, String topic) {
		PTopic pTopic = pTopicDao.findByName(topic);
		if (pTopic != null) {
			throw new IllegalArgumentException("Topic already exists");
		}

		PUser user = pUserService.findByUsername(userName);
		if (user == null) {
			throw new IllegalArgumentException("User does not exist");
		}

		pTopic = new PTopic();
		pTopic.setName(topic);
		pTopic.setUser(user);

		pTopicDao.save(pTopic);
	}

	@Override
	public List<PTopic> all() {
		return pTopicDao.all();
	}

	@Override
	public PTopic findByName(String name) {
		return pTopicDao.findByName(name);
	}
}