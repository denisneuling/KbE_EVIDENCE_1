package de.htw_berlin.aStudent.model;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import de.htw_berlin.aStudent.model.api.AbstractModel;
import de.htw_berlin.aStudent.model.api.view.BaseView;

@Entity
@Table(name = PTopic.TABLENAME)
@SuppressWarnings("serial")
public class PTopic extends AbstractModel<PTopic, Long> {
	public final static String TABLENAME = "PTopic";

	@JsonView(BaseView.class)
	@Id
	@GeneratedValue
	private Long id;

	@Column
	private String name;
	
	@ManyToOne
	private PUser user;
	
	@OneToMany
	private List<PMessage> messages = new LinkedList<PMessage>();

	public PTopic(){
	}
	
	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<PMessage> getMessages() {
		return messages;
	}

	public void setMessages(List<PMessage> messages) {
		this.messages = messages;
	}

	public PUser getUser() {
		return user;
	}

	public void setUser(PUser user) {
		this.user = user;
	}
}
