package com.nns.springboot.sched.service.impl;

import static java.util.stream.Collectors.toList;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.nns.springboot.sched.entity.Message;
import com.nns.springboot.sched.entity.SpamStat;
import com.nns.springboot.sched.repository.MessageRepository;
import com.nns.springboot.sched.repository.SpamStatRepository;
import com.nns.springboot.sched.service.SpamStatService;

@Component(value = "spamStatService")
public class SpamStatServiceImpl implements SpamStatService {

	@Autowired
	private MessageRepository messageRepository;
	
	@Autowired
	private SpamStatRepository spamStatRepository;

	public void loadSpamStat() {

		// Load the message from the database;
		List<Message> messages = messageRepository.findAll();
		
		if(messages ==null || messages.size()==0) {
			return;
		}
		
		String fileName = messages.get(0).getFileName();

		List<String> spamMsgList = messages.stream()
				.filter(msg -> msg.getMessageType().equalsIgnoreCase("SPAM"))
				.map(msg -> msg.getMessage()).collect(toList());

		// Get All the words from the list.
		List<List<String>> wordList = spamMsgList.stream().map(strMessage -> Arrays.asList(strMessage.split("\\s+")))
				.collect(toList());

		List<String> words = wordList.stream().flatMap(Collection::stream).collect(toList());
		
		Map<String, Long> wordsCount = words.stream().collect(Collectors.groupingBy(
                Function.identity(), Collectors.counting()));
		
		
		List<SpamStat> spamStatList = new ArrayList();
		
		Timestamp timestamp = new Timestamp(System.currentTimeMillis());
		wordsCount.forEach( (key,val) -> {
			SpamStat spamStat = new SpamStat();
			spamStat.setWord(key);
			spamStat.setWordCounts(val.intValue());
			spamStat.setFileName(fileName);
			spamStat.setLoadTimestamp(timestamp);
			spamStatList.add(spamStat);
			
		});
		
		spamStatRepository.saveAll(spamStatList);

	}

}
