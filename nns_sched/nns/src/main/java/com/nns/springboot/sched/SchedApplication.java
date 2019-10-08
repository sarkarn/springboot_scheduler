package com.nns.springboot.sched;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.nns.springboot.sched.service.MessageService;
import com.nns.springboot.sched.service.SpamStatService;
import com.nns.springboot.sched.service.SpamTrendService;




@SpringBootApplication
@EnableAutoConfiguration
@EnableScheduling
public class SchedApplication {

    @Autowired
    @Qualifier("messageService")
    MessageService messageService;
    
    @Autowired
    @Qualifier("spamStatService")
    SpamStatService spamStatService;
    
    @Autowired
    @Qualifier("spamTrendService")
    SpamTrendService spamTrendService;
    
    
   
    
	public static void main(String[] args) {
		SpringApplication.run(SchedApplication.class, args);
	}

/*	@Override
	public void run(String... args) throws Exception {
		// TODO Auto-generated method stub
		
		messageService.loadMessage();
		
		spamStatService.loadSpamStat();
		
		spamTrendService.loadSpamTrend();

	} */
	
}
