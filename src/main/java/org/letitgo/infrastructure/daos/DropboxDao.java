package org.letitgo.infrastructure.daos;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import org.letitgo.domain.beans.ActionSuccess;
import org.letitgo.infrastructure.dtos.FileInfosDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

import static java.util.Objects.requireNonNull;

@Component
public class DropboxDao {

	private final DbxClientV2 client;

	@Autowired
	public DropboxDao(Environment environment) {
		DbxRequestConfig config = DbxRequestConfig.newBuilder("memini/1.0").build();
		this.client = new DbxClientV2(config, requireNonNull(environment.getProperty("dropbox.access-token")));
	}

	public ActionSuccess uploadFile(FileInfosDTO fileInfosDTO) {
		try {
			client.files().uploadBuilder("/" + fileInfosDTO.getFileName()).uploadAndFinish(fileInfosDTO.getFile());
			return new ActionSuccess(true);
		} catch (Exception e) {
			return new ActionSuccess(false, Optional.ofNullable(e.getMessage()));
		}
	}

	public ActionSuccess deleteFile(String fileName) {
		try {
			this.client.files().deleteV2(fileName);

			return new ActionSuccess(true);
		} catch (Exception e) {
			return new ActionSuccess(false, Optional.ofNullable(e.getMessage()));
		}
	}

	public ActionSuccess deleteMediaByMediaNames(List<String> mediaNames) {
		List<ActionSuccess> actionSuccesses = mediaNames.stream().map(fileName -> this.deleteFile("/" + fileName)).toList();

		List<ActionSuccess> actionsFailed = actionSuccesses.stream().filter(actionSuccess -> !actionSuccess.success()).toList();

		if (actionsFailed.isEmpty()) {
			return new ActionSuccess(true);
		} else {
			return new ActionSuccess(false, Optional.of("La totalité ou une partie des fichiers n'a pas été supprimée, veuillez réessayer"));
		}
	}

}
