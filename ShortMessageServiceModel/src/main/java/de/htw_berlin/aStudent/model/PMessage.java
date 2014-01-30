package de.htw_berlin.aStudent.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import org.dozer.Mapping;

import com.fasterxml.jackson.annotation.JsonView;

import de.htw_berlin.aStudent.model.api.AbstractModel;
import de.htw_berlin.aStudent.model.api.view.BaseView;

@Entity
@Table(name = PMessage.TABLENAME)
@SuppressWarnings("serial")
public class PMessage extends AbstractModel<PMessage, Long> {
	public final static String TABLENAME = "PMessage";

	@JsonView(BaseView.class)
	@Id
	@GeneratedValue
	@Mapping("messageId")
	private Long id;

	@Column
	@Temporal(TemporalType.TIMESTAMP)
	@Mapping("date")
	private Date date;

	@Column
	@Mapping("content")
	private String content;

	@ManyToOne
	@Mapping("topic")
	private PTopic topic;

	@ManyToOne
	@Mapping("user")
	private PUser user;

	@Column
	@Mapping("origin")
	private boolean origin = false;
	
	@OneToOne
	private PMessage predecessor;

	@OneToOne(orphanRemoval=true)
	private PMessage successor;

	public PMessage(){
	}
	
	@Override
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public PTopic getTopic() {
		return topic;
	}

	public void setTopic(PTopic topic) {
		this.topic = topic;
	}

	public PUser getUser() {
		return user;
	}

	public void setUser(PUser user) {
		this.user = user;
	}
	
	public boolean isOrigin() {
		return origin;
	}

	public void setOrigin(boolean origin) {
		this.origin = origin;
	}

	public PMessage getPredecessor() {
		return predecessor;
	}

	public void setPredecessor(PMessage predecessor) {
		this.predecessor = predecessor;
	}

	public PMessage getSuccessor() {
		return successor;
	}

	public void setSuccessor(PMessage successor) {
		this.successor = successor;
	}
}
