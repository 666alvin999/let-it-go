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
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
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
	public void shouldSaveMemorySuccessfully() {
		// Arrange
		Memory memory = this.getMemory(new Content("salut c'est cool"), new MediaName("test_img.jpg"));

		MemoryDTO memoryDTO = this.getMemoryDTO();

		when(this.memoryMapper.mapToDTO(memory)).thenReturn(memoryDTO);
		when(this.memoryDao.save(memoryDTO)).thenReturn(new ActionSuccess(true));

		// Act
		ActionSuccess actualActionSuccess = this.memoryAdapter.save(memory);

		// Assert
		ActionSuccess expectedMemory = new ActionSuccess(true);

		assertThat(actualActionSuccess).isEqualTo(expectedMemory);
	}

	@Test
	public void shouldUploadMediaSuccessfully() {
		// Arrange
		FileInfos fileInfos = this.getFileInfos();

		FileInfosDTO fileInfosDTO = this.getFileInfosDTO();

		when(this.fileInfosMapper.mapToDTO(fileInfos)).thenReturn(fileInfosDTO);
		when(this.dropboxDao.uploadFile(fileInfosDTO)).thenReturn(new ActionSuccess(true));

		// Act
		ActionSuccess actualActionSuccess = this.memoryAdapter.uploadMedia(fileInfos);

		// Assert
		ActionSuccess expectedActionSuccess = new ActionSuccess(true);

		assertThat(actualActionSuccess).isEqualTo(expectedActionSuccess);
	}

	@Test
	public void shouldDeleteMemorySuccessfully() {
		// Arrange
		Memory memory = this.getMemory(new Content(null), new MediaName(null));

		MemoryDTO memoryDTO = this.getMemoryDTO();

		when(this.memoryMapper.mapToDTO(memory)).thenReturn(memoryDTO);
		when(this.memoryDao.delete(memoryDTO)).thenReturn(new ActionSuccess(true));

		// Act
		ActionSuccess actualActionSuccess = this.memoryAdapter.delete(memory);

		// Assert
		ActionSuccess expectedMemory = new ActionSuccess(true);

		assertThat(actualActionSuccess).isEqualTo(expectedMemory);
	}

	@Test
	public void shouldDeleteMediaSuccessfully() {
		// Arrange
		FileInfos fileInfos = this.getFileInfos();

		FileInfosDTO fileInfosDTO = this.getFileInfosDTO();

		when(this.fileInfosMapper.mapToDTO(fileInfos)).thenReturn(fileInfosDTO);
		when(this.dropboxDao.deleteFile("/" + fileInfosDTO.getFileName())).thenReturn(new ActionSuccess(true));

		// Act
		ActionSuccess actualActionSuccess = this.memoryAdapter.deleteMedia(fileInfos);

		// Assert
		ActionSuccess expectedActionSuccess = new ActionSuccess(true);

		assertThat(actualActionSuccess).isEqualTo(expectedActionSuccess);
	}

	@Test
	public void shouldDeleteMediasByMediaNamesSuccessfully() {
		// Arrange
		List<String> fileNames = List.of("test_img.jpg", "test_img.jpeg");

		when(this.dropboxDao.deleteMediaByMediaNames(fileNames)).thenReturn(new ActionSuccess(true));

		// Act
		ActionSuccess actualActionSuccess = this.memoryAdapter.deleteMediasByMediaNames(fileNames);

		// Assert
		ActionSuccess expectedActionSuccess = new ActionSuccess(true);

		assertThat(actualActionSuccess).isEqualTo(expectedActionSuccess);
	}

	@Test
	public void shouldGetMediaNamesByAlbumNameAndUsername() {
		// Arrange
		String albumName = "ahamaide's album";
		String username = "ahamaide";
		List<String> mediaNames = List.of("test_img.png");

		when(this.memoryDao.getMediaNamesByAlbumNameAndUsername(albumName, username)).thenReturn(mediaNames);

		// Act
		List<String> actualMediaNames = this.memoryAdapter.getMediaNamesByAlbumNameAndUsername(albumName, username);

		// Assert
		List<String> expectedMediaNames = List.of("test_img.png");

		assertThat(actualMediaNames).isEqualTo(expectedMediaNames);
	}

	private Memory getMemory(Content content, MediaName mediaName) {
		return new Memory(
			new AlbumName("ahamaide's album"),
			new Username("ahamaide"),
			content,
			mediaName,
			new MemoryDatetime(LocalDateTime.of(2024, 1, 1, 12, 12, 12))
		);
	}

	private MemoryDTO getMemoryDTO() {
		return memoryDTO()
			.albumName("ahamaide's album")
			.username("ahamaide")
			.textContent("salut c'est cool")
			.mediaName("test_img.jpg")
			.memoryDatetime("2024-01-01 12:12:12")
			.build();
	}

	@SneakyThrows
	private FileInfos getFileInfos() {
		File file = new File(new FileInputStream("src/test/resources/test_img.png"));

		return new FileInfos(
			file,
			new FileName("test_img.png"),
			new Username("ahamaide")
		);
	}

	@SneakyThrows
	private FileInfosDTO getFileInfosDTO() {
		InputStream file = new FileInputStream("src/test/resources/test_img.png");
		return new FileInfosDTO(file, "ahamaide_test_img.png");
	}

}