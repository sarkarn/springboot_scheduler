package com.nns.springboot.sched;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.nns.springboot.sched.service.MessageService;
import com.nns.springboot.sched.service.SpamStatService;
import com.nns.springboot.sched.service.SpamTrendService;

@Component
public class ScheduledTask {

	@Autowired
	@Qualifier("messageService")
	MessageService messageService;

	@Autowired
	@Qualifier("spamStatService")
	SpamStatService spamStatService;

	@Autowired
	@Qualifier("spamTrendService")
	SpamTrendService spamTrendService;

	@Scheduled(fixedRate = 120000)
	public void loadMessage() {
		messageService.loadMessage();
	}

	@Scheduled(fixedRate = 180000)
	public void deriveSpamStat() {
		spamStatService.loadSpamStat();
	}

	@Scheduled(fixedRate = 300000)
	public void deriveSpamTrend() {
		spamTrendService.loadSpamTrend();
	}
}
