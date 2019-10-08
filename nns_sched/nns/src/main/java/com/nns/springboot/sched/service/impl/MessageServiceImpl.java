package com.nns.springboot.sched.service.impl;

import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantLock;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.nns.springboot.sched.entity.Message;
import com.nns.springboot.sched.repository.MessageRepository;
import com.nns.springboot.sched.service.MessageService;

@Component(value = "messageService")
public class MessageServiceImpl implements MessageService {

	private final Logger log = LoggerFactory.getLogger(MessageServiceImpl.class);

	ReentrantLock lock = new ReentrantLock();

	@Autowired
	private MessageRepository messageRepository;

	@Autowired
	private Environment env;

	/**
	 * Get the files list in the order they have arrived so that Files would be
	 * processed in the order they have arrived.
	 * 
	 * @return
	 * @throws IOException
	 */
	private List<Path> getFilesToProcess() throws IOException {

		String messageFileSrcDir = env.getProperty("message.file.src");

		log.debug("Source File Directory" + messageFileSrcDir);

		Stream<Path> paths = Files.walk(Paths.get(messageFileSrcDir));

		List<Path> pathList = paths.filter(Files::isRegularFile).sorted((p1, p2) -> {
			int val = 0;
			if (p1.toFile().lastModified() > p2.toFile().lastModified()) {
				val = 1;
			}
			if (p1.toFile().lastModified() < p2.toFile().lastModified()) {
				val = -1;
			}
			if (p1.toFile().lastModified() == p2.toFile().lastModified()) {
				val = 0;
			}

			return val;

		}).collect(toList());

		return pathList;

	}

	/**
	 * Load the files content Mark the files which cannot be opened and archived
	 * them in the failed location. Separate out the bad records with marking them
	 * message type BAD.
	 */
	public void loadMessage() {
		try {

			lock.tryLock(5, TimeUnit.MINUTES);

			List<Path> pathList = null;
			try {
				pathList = getFilesToProcess();
			} catch (IOException e) {
				e.printStackTrace();
				log.error("Exception : accessing source directory" + e.toString());
				throw new Exception("Error accessing source directory");
			}

			List<Message> messageList = new ArrayList();
			Timestamp timestamp = new Timestamp(System.currentTimeMillis());
			final Pattern wordPattern = Pattern.compile("[a-zA-Z]+");

			for (Path path : pathList) {

				try {
					messageList.addAll(Files.lines(path).map(p -> {

						Matcher m1 = wordPattern.matcher(p);

						String firstWord = "";
						if (m1.find()) {
							firstWord = m1.group();
						}

						String restWord = p.substring(firstWord.length()).trim();

						Message message = new Message();
						message.setMessage(restWord);
						message.setLoadTimestamp(timestamp);
						message.setFileName(path.toFile().getName());

						if ("MSG".equalsIgnoreCase(firstWord) || "SPAM".equalsIgnoreCase(firstWord)) {
							message.setMessageType(firstWord.toUpperCase());

						} else {
							message.setMessageType("BAD");
						}

						return message;
					}).collect(toList()));
				} catch (IOException io) {
					io.printStackTrace();
					log.error("Unable to open the file, file might be corrupted" + io.toString());

					// move the file in the failed location.
					String errorFilepath = env.getProperty("message.file.proc.failed");
					Path errorFilePath = FileSystems.getDefault().getPath(errorFilepath);
					Files.move(path, errorFilePath, StandardCopyOption.REPLACE_EXISTING);
				}
			}
			messageRepository.saveAll(messageList);
			
			//Move all processed files in the success folders
			//code need to be implemented
		} catch (Exception ex) {
              ex.printStackTrace();
              log.error("Exception while processing message : "+ ex.toString());
		} finally {
			lock.unlock();
		}
	}

}
