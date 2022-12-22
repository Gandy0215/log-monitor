package me.gandy.logreader;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class LogReaderApplication implements CommandLineRunner {

	@Value("${app.log.name}")
	private String serverName;
	@Value("${app.log.path}")
	private String logFilePath;

	@Autowired
	me.gandy.logreader.service.LogFileService logFileService;

	public static void main(String[] args) {
		SpringApplication.run(LogReaderApplication.class, args);
	}

	@Override
	public void run(String... arg) {

		System.out.println("시작!!");
		try {
			logFileService.monitorLogfile(serverName, logFilePath);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
