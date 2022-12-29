package me.gandy.logreader;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import me.gandy.logreader.component.LogWebClient;
import me.gandy.logreader.service.LogFileService;

@SpringBootApplication
public class LogReaderApplication implements CommandLineRunner {

	@Value("${app.log.name}")
	private String[] serverName;
	@Value("${app.log.path}")
	private String[] logFilePath;

	@Autowired
	LogWebClient logWeblclent;

	me.gandy.logreader.service.LogFileService logFileService;

	public static void main(String[] args) {
		// SpringApplication.run(LogReaderApplication.class, args);
		SpringApplication application = new SpringApplication(LogReaderApplication.class);
		application.setWebApplicationType(WebApplicationType.NONE);
		application.run();

	}

	@Override
	public void run(String... arg) {
		ArrayList<Thread> readingThread = new ArrayList<>();

		System.out.println("LENGTH :: " + serverName.length);
		for (int idx = 0; idx < serverName.length; idx++) {

			System.out.println("시작!! " + serverName[idx]);
			Thread logFileThread = new LogFileService(logWeblclent, serverName[idx], logFilePath[idx]);

			logFileThread.start();
			readingThread.add(logFileThread);

		}
	}
}
