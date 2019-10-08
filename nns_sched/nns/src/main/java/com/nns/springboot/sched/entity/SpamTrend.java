package com.nns.springboot.sched.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "spam_trend")
public class SpamTrend {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
    private int hamCounts;
    private int spamCounts;
    private Timestamp loadTimestamp;
    private String fileName;
    
    @Column(name="id, nullable = false")
    public Long getId() {
		return id;
	}
    
	public void setId(Long id) {
		this.id = id;
	}

	@Column(name="ham_counts")
	public int getHamCounts() {
		return hamCounts;
	}

	public void setHamCounts(int hamCounts) {
		this.hamCounts = hamCounts;
	}

	@Column(name="spam_counts")
	public int getSpamCounts() {
		return spamCounts;
	}

	public void setSpamCounts(int spamCounts) {
		this.spamCounts = spamCounts;
	}

	@Column(name="load_timestamp, nullable = false")
	public Timestamp getLoadTimestamp() {
		return loadTimestamp;
	}

	public void setLoadTimestamp(Timestamp loadTimestamp) {
		this.loadTimestamp = loadTimestamp;
	}

	@Column(name="file_name, nullable = false")
	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	

}
