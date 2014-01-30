package de.htw_berlin.aStudent.service.impl;

import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import de.htw_berlin.aStudent.dao.PMessageDao;
import de.htw_berlin.aStudent.model.PMessage;
import de.htw_berlin.aStudent.model.PTopic;
import de.htw_berlin.aStudent.model.PUser;
import de.htw_berlin.aStudent.service.PMessageService;
import de.htw_berlin.aStudent.service.PTopicService;
import de.htw_berlin.aStudent.service.PUserService;
import de.htw_berlin.f4.ai.kbe.kurznachrichten.AuthorizationException;

@Service
public class PMessageServiceImpl implements PMessageService{

	@Autowired
	private PMessageDao messageDao;
	
	@Autowired
	private PUserService pUserService;
	
	@Autowired
	private PTopicService pTopicService;
	
	@Override
	public Long createMessage(String userName, String message, String topic) throws IllegalArgumentException {

		PUser user = pUserService.findByUsername(userName);
		if(user == null){
			throw new IllegalArgumentException("User does not exist");
		}
		
		PTopic ptopic = pTopicService.findByName(topic);
		if(ptopic == null){
			throw new IllegalArgumentException("Topic does not exist");
		}
		
		PMessage pMessage = new PMessage();
		pMessage.setUser(user);
		pMessage.setOrigin(true);
		pMessage.setDate(new Date());
		pMessage.setTopic(ptopic);
		
		messageDao.save(pMessage);
		
		return pMessage.getId();
	}

	@Override
	public Long respondToMessage(String userName, String message, Long predecessor) {
		
		PUser user = pUserService.findByUsername(userName);
		if(user == null){
			throw new IllegalArgumentException("User does not exist");
		}
		
		PMessage predecessorMessage = messageDao.findById(predecessor);
		if(predecessorMessage == null){
			throw new IllegalArgumentException("Predeceeding message does not exist");
		}
		
		if(!predecessorMessage.isOrigin()){
			throw new IllegalArgumentException("Predeceeding message is not origin");
		}
		
		PMessage newMessage = new PMessage();
		newMessage.setPredecessor(predecessorMessage);
		newMessage.setContent(message);
		newMessage.setDate(new Date());
		newMessage.setUser(user);
		newMessage.setTopic(predecessorMessage.getTopic());
		
		messageDao.save(newMessage);
		
		return newMessage.getId();
	}

	@Override
	public void deleteMessage(String userName, Long messageId) throws AuthorizationException{

		PUser user = pUserService.findByUsername(userName);
		if(user == null){
			throw new IllegalArgumentException("User does not exist");
		}
		
		PMessage message = messageDao.findById(messageId);
		if(message == null){
			throw new IllegalArgumentException("Message does not exist");
		}
		
		if(!message.isOrigin()){
			throw new IllegalArgumentException("Message is not origin");
		}
		
		
		if(!message.getUser().equals(user)){
			throw new AuthorizationException("User is not owner");
		}
		
		messageDao.delete(message);
	}

	@Override
	public List<List<PMessage>> getMessageByTopic(String topic, Date since) {
		PTopic ptopic = pTopicService.findByName(topic);
		if(ptopic == null){
			throw new IllegalArgumentException("Topic does not exist");
		}
		
		List<PMessage> pMessages = new LinkedList<PMessage>();
		if(since == null){
			pMessages = messageDao.findMessagesByTopic(topic);
		}else{
			pMessages = messageDao.findMessagesByTopicSince(topic, since);
		}
		
		return splitByDate(pMessages);	
	}
	
	protected List<List<PMessage>> splitByDate(List<PMessage> pMessages){
		List<List<PMessage>> outer = new LinkedList<List<PMessage>>();
		if(pMessages == null){
			return outer;
		}
		
		List<PMessage> inner = null;
		Calendar c = Calendar.getInstance();
		
		boolean init = true;
		for(PMessage message : pMessages){
			if(inner != null && c.after(message.getDate())){
				inner.add(message);
			}else{
				if(init && inner!=null){
					outer.add(inner);
					init = false;
				}
				inner = new LinkedList<PMessage>();
				outer.add(inner);
				
				c.setTime(message.getDate());
				c.add(Calendar.DAY_OF_YEAR, 1);
				Calendar.getInstance().set(Calendar.MINUTE, 0);
				Calendar.getInstance().set(Calendar.HOUR, 0);
				Calendar.getInstance().set(Calendar.SECOND, 0);
				
				inner.add(message);
			}
		}
		
		return outer;
	}
}