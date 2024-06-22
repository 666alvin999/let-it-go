package org.letitgo.infrastructure.adapters;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.letitgo.domain.beans.ActionSuccess;
import org.letitgo.domain.beans.FileInfos;
import org.letitgo.domain.beans.fileinfosfields.File;
import org.letitgo.domain.beans.fileinfosfields.FileName;
import org.letitgo.domain.beans.fileinfosfields.Username;
import org.letitgo.infrastructure.daos.DropboxDao;
import org.letitgo.infrastructure.dtos.FileInfosDTO;
import org.letitgo.infrastructure.mappers.FileInfosMapper;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.FileInputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.letitgo.infrastructure.dtos.FileInfosDTO.fileInfosDTO;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class MemoryAdapterTest {

	private MemoryAdapter memoryAdapter;

	@Mock
	private FileInfosMapper fileInfosMapper;

	@Mock
	private DropboxDao dropboxDao;

	@BeforeEach
	public void setUp() {
		this.memoryAdapter = new MemoryAdapter(dropboxDao, fileInfosMapper);
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