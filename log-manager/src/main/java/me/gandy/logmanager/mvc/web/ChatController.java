package me.gandy.logmanager.mvc.web;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import me.gandy.logmanager.mvc.infra.ServerLogRepository;
import me.gandy.logmanager.mvc.model.ServerLog;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

@RequiredArgsConstructor
@RestController
public class ChatController {

	private final ServerLogRepository logRepository;

	@CrossOrigin
	@GetMapping(value="/log/{servername}", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<ServerLog> getLog(@PathVariable String servername) {
		return logRepository.findByServername(servername).subscribeOn(Schedulers.boundedElastic());
	}

	@CrossOrigin
	@PostMapping(value = "/log/")
	public Mono<ServerLog> saveLog(@RequestBody ServerLog serverLog) {
		return logRepository.save(serverLog);
	}

}
