package me.gandy.logmanager.mvc.model;

import java.time.LocalDateTime;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collection = "serverlog")
public class ServerLog {

	@Id
	private String id;
	private String servername;
	private String logline;

	private LocalDateTime crtDtm;
}
