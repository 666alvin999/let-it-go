package org.letitgo.application.mappers.in;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.letitgo.domain.beans.FileInfos;
import org.letitgo.domain.beans.fileinfosfields.File;
import org.letitgo.domain.beans.fileinfosfields.FileName;
import org.letitgo.domain.beans.fileinfosfields.Username;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

class SaveFileFormMapperTest {

	private SaveFileFormMapper saveFileFormMapper;

	@BeforeEach
	public void setUp() {
		this.saveFileFormMapper = new SaveFileFormMapper();
	}

	@Test
	@SneakyThrows
	public void shouldMapToFileInfos() {
		// Arrange
		String fileName = "test_img.png";
		MultipartFile multipartFile = new MockMultipartFile("test_img", new FileInputStream("src/test/resources/test_img.png"));
		String username = "ahamaide";

		// Act
		FileInfos actualFileInfos = this.saveFileFormMapper.mapToFileInfos(multipartFile, username, fileName);

		// Assert
		FileInfos expectedFileInfos = new FileInfos(
			new File(new FileInputStream("src/test/resources/test_img.png")),
			new FileName("test_img.png"),
			new Username("ahamaide")
		);

		assertAll(
			() -> assertThat(actualFileInfos.file().value().readAllBytes()).isEqualTo(expectedFileInfos.file().value().readAllBytes()),
			() -> assertThat(actualFileInfos.fileName().value()).isEqualTo(expectedFileInfos.fileName().value()),
			() -> assertThat(actualFileInfos.username().value()).isEqualTo(expectedFileInfos.username().value())
		);
	}

}