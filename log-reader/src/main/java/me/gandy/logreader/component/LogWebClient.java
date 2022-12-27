package me.gandy.logreader.component;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import me.gandy.logreader.model.ServerLog;
import reactor.core.publisher.Mono;

@Component
public class LogWebClient {
/*
	@Value("${app.server.url}")
	private String SERVER_URL = "http://localhost:9991";
*/
	@Value("${app.server.url}")
	private String SERVER_URL;
	WebClient webClient;

	public LogWebClient() {
		webClient = null;
	}

	private void setWebClient() {
		System.out.println("SERVER_URL :: " + SERVER_URL);

		this.webClient = WebClient.builder()
			.baseUrl(SERVER_URL)
			// .defaultCookie("쿠키키","쿠키값")
			.defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.build();
	}

	public String sendLogLine(ServerLog serverLog) {
		if (webClient == null) {
			setWebClient();
		}

		ServerLog result = this.webClient
			.post()
			.uri("/log/")
			//			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.body(Mono.just(serverLog), ServerLog.class)
			.retrieve()
			.bodyToMono(ServerLog.class)
			.log()
			.block();
		return result.toString();
	}

	public void deleteLogLine(String serverName) {
		if (webClient == null) {
			setWebClient();
		}

		this.webClient
			.method(HttpMethod.DELETE)
			.uri("/log/")
			//			.header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
			.body(Mono.just(serverName), String.class)
			.retrieve()
			.toEntity(Void.class)
			;
	}
}
