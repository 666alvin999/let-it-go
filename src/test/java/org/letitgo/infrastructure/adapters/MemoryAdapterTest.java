package org.letitgo.infrastructure.adapters;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.letitgo.domain.beans.ActionSuccess;
import org.letitgo.domain.beans.FileInfos;
import org.letitgo.domain.beans.Memory;
import org.letitgo.domain.beans.fileinfosfields.File;
import org.letitgo.domain.beans.fileinfosfields.FileName;
import org.letitgo.domain.beans.memoryfields.AlbumName;
import org.letitgo.domain.beans.memoryfields.Content;
import org.letitgo.domain.beans.memoryfields.MediaName;
import org.letitgo.domain.beans.memoryfields.MemoryDatetime;
import org.letitgo.domain.beans.userfields.Username;
import org.letitgo.infrastructure.daos.DropboxDao;
import org.letitgo.infrastructure.daos.MemoryDao;
import org.letitgo.infrastructure.dtos.FileInfosDTO;
import org.letitgo.infrastructure.dtos.MemoryDTO;
import org.letitgo.infrastructure.mappers.FileInfosMapper;
import org.letitgo.infrastructure.mappers.MemoryMapper;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.FileInputStream;
import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.letitgo.infrastructure.dtos.FileInfosDTO.fileInfosDTO;
import static org.letitgo.infrastructure.dtos.MemoryDTO.memoryDTO;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MemoryAdapterTest {

	private MemoryAdapter memoryAdapter;

	@Mock
	private MemoryDao memoryDao;

	@Mock
	private MemoryMapper memoryMapper;

	@Mock
	private FileInfosMapper fileInfosMapper;

	@Mock
	private DropboxDao dropboxDao;

	@BeforeEach
	public void setUp() {
		this.memoryAdapter = new MemoryAdapter(memoryDao, memoryMapper, dropboxDao, fileInfosMapper);
	}

	@Test
	public void shouldDeleteMemorySuccessfully() {
		// Arrange
		Memory memory = new Memory(
			new AlbumName("ahamaide's album"),
			new Username("ahamaide"),
			new Content(null),
			new MediaName(null),
			new MemoryDatetime(LocalDateTime.of(2024, 1, 1, 12, 12, 12))
		);

		MemoryDTO memoryDTO = memoryDTO()
			.albumName("ahamaide's album")
			.username("ahamaide")
			.memoryDatetime("2024-01-01 12:12:12")
			.build();

		when(this.memoryMapper.mapToDTO(memory)).thenReturn(memoryDTO);
		when(this.memoryDao.delete(memoryDTO)).thenReturn(new ActionSuccess(true));

		// Act
		ActionSuccess actualActionSuccess = this.memoryAdapter.delete(memory);

		// Assert
		ActionSuccess expectedMemory = new ActionSuccess(true);

		assertThat(actualActionSuccess).isEqualTo(expectedMemory);
	}

	@Test
	public void shouldSaveMemorySuccessfully() {
		// Arrange
		Memory memory = new Memory(
			new AlbumName("ahamaide's album"),
			new Username("ahamaide"),
			new Content("salut c'est cool"),
			new MediaName("test_img.jpg"),
			new MemoryDatetime(LocalDateTime.of(2024, 1, 1, 12, 12, 12))
		);

		MemoryDTO memoryDTO = memoryDTO()
			.albumName("ahamaide's album")
			.username("ahamaide")
			.textContent("salut c'est cool")
			.mediaName("test_img.jpg")
			.memoryDatetime("2024-01-01 12:12:12")
			.build();

		when(this.memoryMapper.mapToDTO(memory)).thenReturn(memoryDTO);
		when(this.memoryDao.save(memoryDTO)).thenReturn(new ActionSuccess(true));

		// Act
		ActionSuccess actualActionSuccess = this.memoryAdapter.save(memory);

		// Assert
		ActionSuccess expectedMemory = new ActionSuccess(true);

		assertThat(actualActionSuccess).isEqualTo(expectedMemory);
	}

	@Test
	@SneakyThrows
	public void shouldUploadFileSuccessfully() {
		// Arrange
		File file = new File(new FileInputStream("src/test/resources/test_img.png"));

		FileInfos fileInfos = new FileInfos(
			file,
			new FileName("test_img.png"),
			new Username("ahamaide")
		);

		FileInfosDTO fileInfosDTO = fileInfosDTO()
			.file(file.value())
			.fileName("ahamaide_test_img.png")
			.build();

		when(this.fileInfosMapper.mapToDTO(fileInfos)).thenReturn(fileInfosDTO);
		when(this.dropboxDao.uploadFile(fileInfosDTO)).thenReturn(new ActionSuccess(true));

		// Act
		ActionSuccess actualActionSuccess = this.memoryAdapter.uploadFile(fileInfos);

		// Assert
		ActionSuccess expectedActionSuccess = new ActionSuccess(true);

		assertThat(actualActionSuccess).isEqualTo(expectedActionSuccess);
	}

}