package de.htw_berlin.aStudent.service.impl;

import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.when;

import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import de.htw_berlin.aStudent.dao.PMessageDao;
import de.htw_berlin.aStudent.model.PMessage;
import de.htw_berlin.aStudent.model.PTopic;
import de.htw_berlin.aStudent.service.PTopicService;
import de.htw_berlin.aStudent.service.PUserService;

@RunWith(BlockJUnit4ClassRunner.class)
public class PMessageServiceTest {

	@InjectMocks
	private PMessageServiceImpl pMessageService;

	@Mock
	private PMessageDao messageDao;

	@Mock
	private PUserService pUserService;

	@Mock
	private PTopicService pTopicService;
	
	protected String topic = "the holy grale";
	protected String anotherTopic = "42";
	protected List<PMessage> values;
	protected int max = 2;
	
	@Before
	public void setUp(){
		values = new LinkedList<PMessage>();
		PTopic top = new PTopic();
		PMessage origin = null;
		top.setName(topic);
		for(int i = 0 ; i < max ; i++){
			PMessage m = new PMessage();
			m.setTopic(top);
			m.setOriginMesssage(origin);
			m.setOrigin(i <= 0);
			if(i <= 0){
				origin = m;
			}
			Date date = randomDate();
			m.setDate(date);
			values.add(m);
		}
		
		MockitoAnnotations.initMocks(this);
	}
	
	@Test
	public void getMessageByTopic_all_msg_for_topic(){
		when(pTopicService.findByName(topic)).thenReturn(new PTopic());
		when(messageDao.findMessagesByTopic(topic)).thenReturn(values);
		when(messageDao.findMessagesByTopicSince(eq(topic), any(Date.class))).thenReturn(values);
		
		Assert.assertEquals(2, size(pMessageService.getMessageByTopic(topic, new Date())));
	}
	
	@Test
	public void getMessageByTopic_no_msgs_for_topic(){
		when(pTopicService.findByName(anotherTopic)).thenReturn(new PTopic());
		when(messageDao.findMessagesByTopic(topic)).thenReturn(values);
		when(messageDao.findMessagesByTopicSince(eq(topic), any(Date.class))).thenReturn(values);
		
		Assert.assertEquals(0, size(pMessageService.getMessageByTopic(anotherTopic, new Date())));
	}
	
	protected int size(List<List<PMessage>> values){
		int size = 0 ;
		for(List<PMessage> inner : values){
			size += inner.size();
		}
		return size;
	}
	
	protected Date randomDate(){
		int year = randBetween(2012, 2014);
        int month = randBetween(0, 11);
        int hour = randBetween(0, 23); 
        int min = randBetween(0, 59);
        int sec = randBetween(0, 59);

        GregorianCalendar gc = new GregorianCalendar(year, month, 1);
        int day = randBetween(1, gc.getActualMaximum(GregorianCalendar.DAY_OF_MONTH));

        gc.set(year, month, day, hour, min,sec);

        return gc.getTime();
	}
	
	public static int randBetween(int start, int end) {
		return start + (int)Math.round(Math.random() * (end - start));
	}
}
