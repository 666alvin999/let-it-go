package org.letitgo.infrastructure.mappers;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.letitgo.domain.beans.FileInfos;
import org.letitgo.domain.beans.fileinfosfields.File;
import org.letitgo.domain.beans.fileinfosfields.FileName;
import org.letitgo.domain.beans.fileinfosfields.Username;
import org.letitgo.infrastructure.dtos.FileInfosDTO;

import java.io.FileInputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.letitgo.infrastructure.dtos.FileInfosDTO.fileInfosDTO;

class FileInfosMapperTest {

	private FileInfosMapper fileInfosMapper;

	@BeforeEach
	public void setUp() {
		this.fileInfosMapper = new FileInfosMapper();
	}

	@Test
	@SneakyThrows
	public void shouldMapFileInfosToFileInfosDTO() {
		// Arrange
		FileInfos fileInfos = new FileInfos(
			new File(new FileInputStream("src/test/resources/test_img.png")),
			new FileName("test_img.png"),
			new Username("ahamaide")
		);

		// Act
		FileInfosDTO actualFileInfosDTO = this.fileInfosMapper.mapToDTO(fileInfos);

		// Assert
		FileInfosDTO expectedFileInfosDTO = fileInfosDTO()
			.file(new FileInputStream("src/test/resources/test_img.png"))
			.fileName("ahamaide_test_img.png")
			.build();

		assertAll(
			() -> assertThat(actualFileInfosDTO.getFile().readAllBytes()).isEqualTo(expectedFileInfosDTO.getFile().readAllBytes()),
			() -> assertThat(actualFileInfosDTO.getFileName()).isEqualTo(expectedFileInfosDTO.getFileName())
		);
	}

}