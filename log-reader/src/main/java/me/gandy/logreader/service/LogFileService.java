package me.gandy.logreader.service;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import me.gandy.logreader.component.LogWebClient;
import me.gandy.logreader.model.ServerLog;

@Slf4j
public class LogFileService extends Thread {

	LogWebClient logWeblclent;
	String serverName;
	String logFilePath;

	public LogFileService(LogWebClient logWeblclent, String serverName, String logFilePath) {
		this.logWeblclent = logWeblclent;
		this.serverName = serverName;
		this.logFilePath = logFilePath;
	}

	@SneakyThrows
	public void run() {

		File logFile = new File(logFilePath);
		logWeblclent.deleteLogLine(serverName);

		long filePointer = 0;
		if (logFile.length() > 30) {
			filePointer = logFile.length() - 30;
		}

		while (true) {

			if (!logFile.exists()) {
				System.out.println("File Not Found :: " + logFilePath);
				Thread.sleep(5000);
			}

			if (filePointer > logFile.length()) {
				filePointer = 0;
			}

			if (filePointer == logFile.length() - 1) {
				Thread.sleep(1000);
				continue;
			}
			filePointer = readingLogFile(serverName, logFile, filePointer);
		}
	}

	private long readingLogFile(String serverName, File logFile, long filePointer) throws IOException {
		RandomAccessFile reader = new RandomAccessFile(logFile, "r");
		reader.seek(filePointer);

		String strLogline;

		while ((strLogline = reader.readLine()) != null) {

			requestFileLineSave(serverName, strLogline);
			filePointer = reader.getFilePointer();
		}
		reader.close();
		return filePointer;
	}

	private void requestFileLineSave(String serverName, String strLogline) throws UnsupportedEncodingException {
		ServerLog serverLog = new ServerLog().builder()
			.servername(serverName)
			.logline(new String(strLogline.getBytes("ISO-8859-1"), "UTF-8"))
			//.logline(new String(strLogline.getBytes()))
			.build();

		String result = logWeblclent.sendLogLine(serverLog);
		log.info(result);
	}
}
