package de.htw_berlin.aStudent.model;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.dozer.Mapping;

import com.fasterxml.jackson.annotation.JsonView;

import de.htw_berlin.aStudent.model.api.AbstractModel;
import de.htw_berlin.aStudent.model.api.view.BaseView;

@Entity
@Table(name = PUser.TABLENAME)
@SuppressWarnings("serial")
public class PUser extends AbstractModel<PUser, Long> {
	public final static String TABLENAME = "PUser";

	@JsonView(BaseView.class)
	@Id
	@GeneratedValue
	private Long id;

	@Column
	@Mapping("name")
	private String name;
	
	@Column
	@Mapping("city")
	private String city;
	
	@OneToMany
	private List<PMessage> messages = new LinkedList<PMessage>();
	
	@OneToMany
	private List<PTopic> topics = new LinkedList<PTopic>();
	
	public PUser(){
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

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public List<PMessage> getMessages() {
		return messages;
	}

	public void setMessages(List<PMessage> messages) {
		this.messages = messages;
	}

	@Override
	public String toString() {
		return name;
	}
	
	
}
