package org.letitgo.infrastructure.daos;

import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import org.apache.tomcat.jni.FileInfo;
import org.letitgo.domain.beans.ActionSuccess;
import org.letitgo.infrastructure.dtos.FileInfosDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.io.InputStream;
import java.util.Objects;
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
			return new ActionSuccess(false, Optional.of(e.getMessage()));
		}
	}

}
