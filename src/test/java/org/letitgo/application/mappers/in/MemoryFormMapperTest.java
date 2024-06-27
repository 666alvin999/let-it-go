package org.letitgo.application.mappers.in;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.letitgo.application.dtos.in.MemoryForm;
import org.letitgo.domain.beans.FileInfos;
import org.letitgo.domain.beans.Memory;
import org.letitgo.domain.beans.albumfields.AlbumName;
import org.letitgo.domain.beans.fileinfosfields.File;
import org.letitgo.domain.beans.fileinfosfields.FileName;
import org.letitgo.domain.beans.memoryfields.Content;
import org.letitgo.domain.beans.memoryfields.MediaName;
import org.letitgo.domain.beans.memoryfields.MemoryDatetime;
import org.letitgo.domain.beans.memoryfields.Mood;
import org.letitgo.domain.beans.userfields.Username;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.io.FileInputStream;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.letitgo.application.dtos.in.MemoryForm.saveMemoryForm;

class MemoryFormMapperTest {

	private MemoryFormMapper memoryFormMapper;

	@BeforeEach
	public void setUp() {
		this.memoryFormMapper = new MemoryFormMapper();
	}

	@Test
	@SneakyThrows
	public void shouldMapToFileInfos() {
		// Arrange
		MultipartFile multipartFile = new MockMultipartFile("test_img", new FileInputStream("src/test/resources/test_img.png"));

		MemoryForm memoryForm = saveMemoryForm()
			.multipartFile(multipartFile)
			.username("ahamaide")
			.fileName("test_img.png")
			.build();

		// Act
		FileInfos actualFileInfos = this.memoryFormMapper.mapToFileInfos(memoryForm);

		// Assert
		FileInfos expectedFileInfos = new FileInfos(
			new File(new FileInputStream("src/test/resources/test_img.png")),
			new AlbumName("album1"),
			new FileName("test_img.png"),
			new Username("ahamaide")
		);

		assertAll(
			() -> assertThat(actualFileInfos.file().value().readAllBytes()).isEqualTo(expectedFileInfos.file().value().readAllBytes()),
			() -> assertThat(actualFileInfos.fileName().value()).isEqualTo(expectedFileInfos.fileName().value()),
			() -> assertThat(actualFileInfos.username().value()).isEqualTo(expectedFileInfos.username().value())
		);
	}

	@Test
	public void shouldMapToMemory() {
		// Arrange
		MemoryForm memoryForm = saveMemoryForm()
			.albumName("album")
			.username("ahamaide")
			.content("test content")
			.fileName("test_img.png")
			.memoryDatetime("2024-01-01 12:12:12")
			.mood("happy")
			.build();

		// Act
		Memory actualMemory = this.memoryFormMapper.mapToMemory(memoryForm);

		// Assert
		Memory expectedMemory = new Memory(
			new AlbumName("album"),
			new Username("ahamaide"),
			new Content("test content"),
			new MediaName("test_img.png"),
			new MemoryDatetime(LocalDateTime.of(2024, 1, 1, 12, 12, 12)),
			Mood.HAPPY
		);

		assertThat(actualMemory).isEqualTo(expectedMemory);
	}

}