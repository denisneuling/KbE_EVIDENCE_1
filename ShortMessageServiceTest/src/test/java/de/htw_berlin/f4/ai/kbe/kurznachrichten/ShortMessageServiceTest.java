package de.htw_berlin.f4.ai.kbe.kurznachrichten;

import java.util.Date;

import org.apache.commons.lang.RandomStringUtils;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * 
 * @author Denis Neuling
 * @author Roland Luckenhuber
 */
@Ignore
@RunWith(MockitoJUnitRunner.class)
public class ShortMessageServiceTest {

	private String messageValid = RandomStringUtils.random(200);
	private String messageTooShort = RandomStringUtils.random(9);
	private String messageTooLong = RandomStringUtils.random(255);

	private String usernameTooShort = RandomStringUtils.random(3);
	private String usernameTooLong = RandomStringUtils.random(31);
	private String usernameExists = RandomStringUtils.random(10);
	private String validCity = "valid" + RandomStringUtils.random(15);

	private String topicInvalid = "invalid" + RandomStringUtils.random(20);
	private String topicValid = "valid" + RandomStringUtils.random(15);
	private Date dateValid = new Date();

	private Long messageIdInvalid = 3L;
	private String usernameValid = "valid"+usernameExists ;
	private Long messageIdNotOrigin =2L;
	private String usernameNotFound = "notfound"+usernameExists ;
	private Long messageId = 1L;
	private String usernameNotAuthorized = "notauthorized";
	private String usernameNotExists ="notexist"+usernameExists;
	private String topicExists = "exists"+topicValid;
	private String topicTooShort = RandomStringUtils.random(1);
	private String topicTooLong = RandomStringUtils.random(70);
	
	private String validUserName = "validUserName";
	private String invalidUserName = "invalidUserName";

	private Long predecessorValid = 1L;
	private Long predecessorInvalid = 2L;
	private Long predecessorNotOrigin = 3L;

	// TODO use @InjectMocks on implementated service
	@InjectMocks
	private ShortMessageService shortMessageService;

	@Before
	public void setUp() {
		MockitoAnnotations.initMocks(this);
	}

	@Test(expected = IllegalArgumentException.class)
	public void createMessag_IllegalArgumentExceptione_Message_too_long() {
		shortMessageService.createUser(validUserName, validCity);
		shortMessageService.createTopic(validUserName, topicValid);
		shortMessageService.createMessage(validUserName, messageTooLong, topicValid);
	}

	@Test(expected = IllegalArgumentException.class)
	public void createMessag_IllegalArgumentExceptione_Message_too_short() {
		shortMessageService.createUser(validUserName, validCity);
		shortMessageService.createTopic(validUserName, topicValid);
		shortMessageService.createMessage(validUserName, messageTooShort, topicValid);
	}

	@Test(expected = IllegalArgumentException.class)
	public void createMessag_IllegalArgumentExceptione_User_not_found() {
		shortMessageService.createUser(validUserName, validCity);
		shortMessageService.createTopic(validUserName, topicValid);
		Assert.assertNotNull(shortMessageService.createMessage(validUserName, messageValid, topicValid));
	}

	@Test(expected = NullPointerException.class)
	public void createMessage_NullPointerException_Username_Null() {
		shortMessageService.createTopic(validUserName, topicValid);
		shortMessageService.createMessage(null, messageValid, topicValid);
	}

	@Test(expected = NullPointerException.class)
	public void createMessage_NullPointerException_Message_Null() {
		shortMessageService.createUser(validUserName, validCity);
		shortMessageService.createTopic(validUserName, topicValid);
		shortMessageService.createMessage(validUserName, null, topicValid);
	}

	@Test(expected = NullPointerException.class)
	public void createMessage_NullPointerException_Topic_Null() {
		shortMessageService.createUser(validUserName, validCity);
		shortMessageService.createTopic(validUserName, topicValid);
		shortMessageService.createMessage(validUserName, messageValid, null);
	}

	@Test
	public void createMessage() {
		shortMessageService.createUser(validUserName, validCity);
		shortMessageService.createTopic(validUserName, topicValid);
		Long id = shortMessageService.createMessage(validUserName, messageValid, topicValid);

		Assert.assertNotNull(id);
		Assert.assertTrue(id > 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void respondToMessage_IllegalArgumentException_Message_too_long() {
		shortMessageService.createUser(validUserName, validCity);
		shortMessageService.createTopic(validUserName, topicValid);
		shortMessageService.respondToMessage(validUserName, messageTooLong, predecessorValid);
	}

	@Test(expected = IllegalArgumentException.class)
	public void respondToMessage_IllegalArgumentException_Message_too_short() {
		shortMessageService.createUser(validUserName, validCity);
		shortMessageService.createTopic(validUserName, topicValid);
		shortMessageService.respondToMessage(validUserName, messageTooShort, predecessorValid);
	}

	@Test(expected = IllegalArgumentException.class)
	public void respondToMessage_IllegalArgumentException_User_not_found() {
		shortMessageService.respondToMessage(invalidUserName, messageValid, predecessorValid);
	}

	@Test(expected = IllegalArgumentException.class)
	public void respondToMessage_IllegalArgumentException_Predecessor_not_found() {
		shortMessageService.createUser(validUserName, validCity);
		shortMessageService.respondToMessage(validUserName, messageValid, predecessorInvalid);
	}

	@Test(expected = IllegalArgumentException.class)
	public void respondToMessage_IllegalArgumentException_Predecessor_not_origin() {
		shortMessageService.createUser(validUserName, validCity);
		shortMessageService.respondToMessage(validUserName, messageValid, predecessorNotOrigin);
	}

	@Test(expected = NullPointerException.class)
	public void respondToMessage_NullPointerException_Username_is_null() {
		shortMessageService.respondToMessage(null, messageValid, predecessorValid);
	}

	@Test(expected = NullPointerException.class)
	public void respondToMessage_NullPointerException_Message_is_null() {
		shortMessageService.createUser(validUserName, validCity);
		shortMessageService.createTopic(validUserName, topicValid);
		shortMessageService.createMessage(validUserName, null, topicValid);
	}

	@Test(expected = NullPointerException.class)
	public void respondToMessage_NullPointerException_Predecessor_is_null() {
		shortMessageService.createUser(validUserName, validCity);
		shortMessageService.createMessage(validUserName, messageValid, null);
	}

	@Test
	public void respondToMessage() {
		shortMessageService.createUser(validUserName, validCity);
		Long id = shortMessageService.respondToMessage(validUserName, messageValid, predecessorValid);

		Assert.assertNotNull(id);
		Assert.assertTrue(id > 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void deleteMessage_IllegalArgumentException_Message_not_exist() throws AuthorizationException {
		shortMessageService.createUser(validUserName, validCity);
		shortMessageService.deleteMessage(usernameValid, messageIdInvalid);
	}

	@Test(expected = IllegalArgumentException.class)
	public void deleteMessage_IllegalArgumentException_Message_not_origin() throws AuthorizationException {
		shortMessageService.createUser(validUserName, validCity);
		shortMessageService.deleteMessage(usernameValid, messageIdNotOrigin);
	}

	@Test(expected = IllegalArgumentException.class)
	public void deleteMessage_IllegalArgumentException_User_not_found() throws AuthorizationException {
		shortMessageService.deleteMessage(usernameNotFound, messageId);
	}

	@Test(expected = NullPointerException.class)
	public void deleteMessage_NullPointerException_Username_is_null() throws AuthorizationException {
		shortMessageService.deleteMessage(null, messageId);
	}

	@Test(expected = NullPointerException.class)
	public void deleteMessage_NullPointerException_MessageId_is_null() throws AuthorizationException {
		shortMessageService.createUser(validUserName, validCity);
		shortMessageService.deleteMessage(usernameValid, null);
	}

	@Test(expected = AuthorizationException.class)
	public void deleteMessage_AuthorizationException_User_not_authorized() throws AuthorizationException {
		shortMessageService.createUser(usernameNotAuthorized, validCity);
		shortMessageService.deleteMessage(usernameNotAuthorized, messageId);
	}

	@Test
	public void deleteMessage() throws AuthorizationException {
		shortMessageService.createUser(validUserName, validCity);
		messageId = shortMessageService.createMessage(validUserName, messageValid, topicValid);
		shortMessageService.deleteMessage(usernameExists, messageId);
	}

	@Test(expected = IllegalArgumentException.class)
	public void createTopic_IllegalArgumentException_User_not_exist() {
		shortMessageService.createTopic(usernameNotExists, topicValid);
	}

	@Test(expected = IllegalArgumentException.class)
	public void createTopic_IllegalArgumentException_Topic_exists() {
		shortMessageService.createUser(validUserName, validCity);
		shortMessageService.createTopic(validUserName, topicExists);
		shortMessageService.createTopic(validUserName, topicExists);
	}

	@Test(expected = IllegalArgumentException.class)
	public void createTopic_IllegalArgumentException_Topic_too_short() {
		shortMessageService.createUser(usernameExists, validCity);
		shortMessageService.createTopic(usernameExists, topicTooShort);
	}

	@Test(expected = IllegalArgumentException.class)
	public void createTopic_IllegalArgumentException_Topic_too_long() {
		shortMessageService.createUser(usernameExists, validCity);
		shortMessageService.createTopic(usernameExists, topicTooLong);
	}

	@Test(expected = NullPointerException.class)
	public void createTopic_NullPointerException_Username_is_null() {
		shortMessageService.createTopic(null, topicValid);
	}

	@Test(expected = NullPointerException.class)
	public void createTopic_NullPointerException_Topic_is_null() {
		shortMessageService.createTopic(usernameExists, null);
	}

	@Test
	public void createTopic() {
		shortMessageService.createUser(usernameExists, validCity);
		shortMessageService.createTopic(usernameExists, topicValid);
	}

	@Test
	public void getTopics() {
		Assert.assertNotNull(shortMessageService.getTopics());
	}

	@Test(expected = IllegalArgumentException.class)
	public void getMessageByTopic_IllegalArgumentException_Topic_not_found() {
		shortMessageService.getMessageByTopic(topicInvalid, dateValid);
	}

	@Test(expected = NullPointerException.class)
	public void getMessageByTopic_NullPointerException_Topic_is_null() {
		shortMessageService.getMessageByTopic(null, dateValid);
	}

	@Test
	public void getMessageByTopic() {
		Assert.assertNotNull(shortMessageService.getMessageByTopic(topicValid, dateValid));
	}

	@Test(expected = IllegalArgumentException.class)
	public void createUser_IllegalArgumentException_User_exists() {
		shortMessageService.createUser(usernameExists, validCity);
		shortMessageService.createUser(usernameExists, validCity);
	}

	@Test(expected = IllegalArgumentException.class)
	public void createUser_IllegalArgumentException_Username_too_long() {
		shortMessageService.createUser(usernameTooLong, validCity);
	}

	@Test(expected = IllegalArgumentException.class)
	public void createUser_IllegalArgumentException_Username_too_short() {
		shortMessageService.createUser(usernameTooShort, validCity);
	}

	@Test(expected = NullPointerException.class)
	public void createUser_NullPointerException_Username_is_null() {
		shortMessageService.createUser(null, validCity);
	}

	@Test(expected = NullPointerException.class)
	public void createUser_NullPointerException_City_is_null() {
		shortMessageService.createUser(validUserName, null);
	}

	@Test
	public void createUser() {
		shortMessageService.createUser(validUserName, validCity);
	}

	@Test(expected = IllegalArgumentException.class)
	public void deleteUser_IllegalArgumentException_User_not_found() {
		shortMessageService.deleteUser(invalidUserName);
	}

	@Test
	public void getUsers() {
		Assert.assertNotNull(shortMessageService.getUsers());
	}

}
