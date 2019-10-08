package com.nns.springboot.sched.service.impl;

import static java.util.stream.Collectors.toList;

import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nns.springboot.sched.entity.Message;
import com.nns.springboot.sched.entity.SpamTrend;
import com.nns.springboot.sched.repository.MessageRepository;
import com.nns.springboot.sched.repository.SpamTrendRepository;
import com.nns.springboot.sched.service.SpamTrendService;

@Component(value = "spamTrendService")
public class SpamTrendServiceImpl implements SpamTrendService{

	@Autowired
	private MessageRepository messageRepository;
	
	@Autowired
	private SpamTrendRepository spamTrendRepository;

	public void loadSpamTrend() {

		// Load the message from the database;
		List<Message> messages = messageRepository.findAll();
		
		if(messages ==null || messages.size()==0) {
			return;
		}
		
		String fileName = messages.get(0).getFileName();

		List<String> spamMsgList = messages.stream()
				.filter(msg -> msg.getMessageType().equalsIgnoreCase("SPAM"))
				.map(msg -> msg.getMessage()).collect(toList());
		
		List<String> hamMsgList = messages.stream()
				.filter(msg -> msg.getMessageType().equalsIgnoreCase("MSG"))
				.map(msg -> msg.getMessage()).collect(toList());

		int spamCount = 0;
		if(spamMsgList!=null) {
			spamCount = spamMsgList.size();
		}
		
		int hamCount = 0;
		if(hamMsgList!=null) {
			hamCount = hamMsgList.size();
		}
		
	
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		SpamTrend spamTrend = new SpamTrend();
		spamTrend.setHamCounts(hamCount);
		spamTrend.setSpamCounts(spamCount);
		spamTrend.setFileName(fileName);
		spamTrend.setLoadTimestamp(timestamp);
		spamTrendRepository.save(spamTrend);

	}
}
