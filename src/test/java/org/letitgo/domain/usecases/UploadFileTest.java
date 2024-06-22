package org.letitgo.domain.usecases;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.letitgo.domain.beans.ActionSuccess;
import org.letitgo.domain.beans.FileInfos;
import org.letitgo.domain.beans.fileinfosfields.File;
import org.letitgo.domain.beans.fileinfosfields.FileName;
import org.letitgo.domain.beans.fileinfosfields.Username;
import org.letitgo.domain.ports.MemoryPort;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.FileInputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UploadFileTest {

	private UploadFile uploadFile;

	@Mock
	private MemoryPort memoryPort;

	@BeforeEach
	public void setUp() {
		this.uploadFile = new UploadFile(memoryPort);
	}

	@Test
	@SneakyThrows
	public void shouldUploadFileSuccessfully() {
	    // Arrange
		FileInfos fileInfos = new FileInfos(
			new File(new FileInputStream("src/test/resources/test_img.png")),
			new FileName("test_img.png"),
			new Username("ahamaide")
		);

		when(this.memoryPort.uploadFile(fileInfos)).thenReturn(new ActionSuccess(true));

	    // Act
		ActionSuccess actualActionSuccess = this.uploadFile.execute(fileInfos);

	    // Assert
		ActionSuccess expectedActionSuccess = new ActionSuccess(true);

		assertThat(actualActionSuccess).isEqualTo(expectedActionSuccess);
	}

}