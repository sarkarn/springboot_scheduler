/**
 * 
 */
package com.nns.springboot.sched.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author naren
 *
 */
@Entity
@Table(name = "message")
public class Message {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
    private String messageType;
    private String message;
    private Timestamp loadTimestamp;
    private String fileName;
    
    @Column(name="id, nullable = false")
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@Column(name = "message_type")
	public String getMessageType() {
		return messageType;
	}

	
	public void setMessageType(String messageType) {
		this.messageType = messageType;
	}

	@Column(name = "message") 
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	@Column(name = "load_timestamp")
	public Timestamp getLoadTimestamp() {
		return loadTimestamp;
	}

	public void setLoadTimestamp(Timestamp loadTimestamp) {
		this.loadTimestamp = loadTimestamp;
	}

	@Column(name = "file_name")
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
    
}
