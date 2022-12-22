package me.gandy.logmanager.mvc.infra;

import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;

import me.gandy.logmanager.mvc.model.ServerLog;
import reactor.core.publisher.Flux;

public interface ServerLogRepository extends ReactiveMongoRepository<ServerLog, String> {
	@Tailable
	@Query("{'servername':?0}")
	Flux<ServerLog> findByServername(String servername);

}
