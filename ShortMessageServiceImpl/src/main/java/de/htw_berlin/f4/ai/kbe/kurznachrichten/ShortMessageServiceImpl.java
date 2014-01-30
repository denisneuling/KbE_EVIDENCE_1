package de.htw_berlin.f4.ai.kbe.kurznachrichten;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.dozer.DozerBeanMapper;
import org.jvnet.hk2.annotations.Service;
import org.springframework.beans.factory.annotation.Autowired;

import de.htw_berlin.aStudent.service.PMessageService;
import de.htw_berlin.aStudent.service.PTopicService;
import de.htw_berlin.aStudent.service.PUserService;
import de.htw_berlin.aStudent.util.PojoMapperUtil;

@Service
public class ShortMessageServiceImpl implements ShortMessageService {

	@Autowired
	private DozerBeanMapper dozerBeanMapper;
	
	@Autowired
	private PUserService pUserService;
	
	@Autowired
	private PMessageService pMessageService;
	
	@Autowired
	private PTopicService pTopicService;
	
	@Override
	public Long createMessage(String userName, String message, String topic) {
		if(userName == null || message == null || topic == null){
			throw new NullPointerException("Argument is null");
		}
		
		if(message.length() > 255 ){
			throw new IllegalArgumentException("Message too long");
		}
		
		if(message.length() < 10 ){
			throw new IllegalArgumentException("Message too short");
		}
		
		return pMessageService.createMessage(userName, message, topic);
	}

	@Override
	public Long respondToMessage(String userName, String message, Long predecessor) {
		if(userName == null || message == null || predecessor == null){
			throw new NullPointerException("Argument is null");
		}
		
		if(message.length() > 255 ){
			throw new IllegalArgumentException("Message too long");
		}

		if(message.length() < 10 ){
			throw new IllegalArgumentException("Message too short");
		}
		
		
		return pMessageService.respondToMessage(userName, message, predecessor);
	}

	@Override
	public void deleteMessage(String userName, Long messageId) throws AuthorizationException {
		if(userName == null || messageId == null){
			throw new NullPointerException("Argument is null");
		}
		
		pMessageService.deleteMessage(userName, messageId);
	}

	@Override
	public void createTopic(String userName, String topic) {
		if(topic == null || userName == null){
			throw new NullPointerException("Argument was null");
		}
		
		if(topic.length() < 2){
			throw new IllegalArgumentException("Topic too short");
		}
		
		if(topic.length() > 70){
			throw new IllegalArgumentException("Topic too long");
		}
		
		pTopicService.createTopic(userName, topic);
	}
	
	@Override
	public Set<String> getTopics() {
		return PojoMapperUtil.mapSet(dozerBeanMapper, pTopicService.all(), String.class);
	}

	@Override
	public List<List<Message>> getMessageByTopic(String topic, Date since) {
		if(topic == null){
			throw new NullPointerException("Topic was not set");
		}

		return PojoMapperUtil.mapMatrix(dozerBeanMapper, pMessageService.getMessageByTopic(topic, since), Message.class);
	}

	@Override
	public void createUser(String userName, String city) {
		if(userName == null || city == null){
			throw new NullPointerException("Argument was null");
		}
		
		if(userName.length() > 30){
			throw new IllegalArgumentException("Username too long");
		}
		
		if(userName.length() < 4){
			throw new IllegalArgumentException("Username too short");
		}
		
		pUserService.createUser(userName, city);
	}

	@Override
	public void deleteUser(String userName) {
		pUserService.deleteUser(userName);
	}

	@Override
	public Set<User> getUsers() {
		return PojoMapperUtil.mapSet(dozerBeanMapper, pUserService.all(), User.class);
	}
}
