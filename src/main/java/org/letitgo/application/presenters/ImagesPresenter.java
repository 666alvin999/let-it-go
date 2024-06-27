package org.letitgo.application.presenters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.io.InputStream;

@Slf4j
@Component
public class ImagesPresenter {

	public ResponseEntity<InputStreamResource> present(InputStream inputStream) {
		try {
			HttpHeaders headers = new HttpHeaders();
			headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"medias.zip\"");
			headers.add(HttpHeaders.CONTENT_TYPE, "application/zip");

			return new ResponseEntity<>(new InputStreamResource(inputStream), headers, HttpStatus.OK);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

}
