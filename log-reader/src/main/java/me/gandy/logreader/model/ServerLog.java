package me.gandy.logreader.model;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class ServerLog {

	private String id;
	private String servername;
	private String logline;
	private LocalDateTime crtDtm;

	@Builder
	public ServerLog(String servername, String logline) {
		this.servername = servername;
		this.logline = logline;
		this.crtDtm = LocalDateTime.now();
	}

	@Override
	public String toString() {
		return String.format(
			"ServerLog[id=%s, servername='%s', logline='%s']",
			id, servername, logline);
	}
}
