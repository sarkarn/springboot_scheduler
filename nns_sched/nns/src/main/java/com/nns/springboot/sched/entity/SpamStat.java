package com.nns.springboot.sched.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "spam_stat")
public class SpamStat {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	
    private String word;
    private int wordCounts;
    private Timestamp loadTimestamp;
    private String fileName;
    
    @Column(name="id, nullable = false")
    public Long getId() {
		return id;
	}
    
	public void setId(Long id) {
		this.id = id;
	}
	
	@Column(name="word")
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	
	@Column(name="word_counts, nullable = false")
	public int getWordCounts() {
		return wordCounts;
	}
	public void setWordCounts(int wordCounts) {
		this.wordCounts = wordCounts;
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
