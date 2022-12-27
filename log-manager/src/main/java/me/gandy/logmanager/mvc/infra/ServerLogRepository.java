package me.gandy.logmanager.mvc.infra;

import java.time.LocalDateTime;

import org.springframework.data.mongodb.repository.DeleteQuery;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.data.mongodb.repository.Tailable;

import me.gandy.logmanager.mvc.model.ServerLog;
import reactor.core.publisher.Flux;

public interface ServerLogRepository extends ReactiveMongoRepository<ServerLog, String> {
	@Tailable
	@Query("{$and: [{servername:?0}, {crtDtm: {$gte: ?1}}] }")
	Flux<ServerLog> findByServername(String servername, LocalDateTime baseDtm);

	@DeleteQuery
	void deleteByServername(String serverName);
}
