package de.htw_berlin.aStudent.service;

import java.util.Date;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import de.htw_berlin.aStudent.model.PMessage;
import de.htw_berlin.f4.ai.kbe.kurznachrichten.AuthorizationException;

@Transactional
public interface PMessageService {

	public Long createMessage(String userName, String message, String topic) throws IllegalArgumentException;

	public Long respondToMessage(String userName, String message, Long predecessor);

	public void deleteMessage(String userName, Long messageId) throws AuthorizationException;

	public List<List<PMessage>> getMessageByTopic(String topic, Date since);
}