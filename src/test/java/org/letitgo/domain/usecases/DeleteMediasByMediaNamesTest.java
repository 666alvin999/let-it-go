package org.letitgo.domain.usecases;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.letitgo.domain.beans.ActionSuccess;
import org.letitgo.domain.beans.FileInfos;
import org.letitgo.domain.beans.fileinfosfields.File;
import org.letitgo.domain.beans.fileinfosfields.FileName;
import org.letitgo.domain.beans.userfields.Username;
import org.letitgo.domain.ports.MemoryPort;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.FileInputStream;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeleteMediasByMediaNamesTest {

	private DeleteMediasByMediaNames deleteMediasByMediaNames;

	@Mock
	private MemoryPort memoryPort;

	@BeforeEach
	public void setUp() {
		this.deleteMediasByMediaNames = new DeleteMediasByMediaNames(memoryPort);
	}

	@Test
	@SneakyThrows
	public void shouldUploadFileSuccessfully() {
		// Arrange
		List<String> mediaNames = List.of("test_img.png", "test_img.jpg");

		when(this.memoryPort.deleteMediasByMediaNames(mediaNames)).thenReturn(new ActionSuccess(true));

		// Act
		ActionSuccess actualActionSuccess = this.deleteMediasByMediaNames.execute(mediaNames);

		// Assert
		ActionSuccess expectedActionSuccess = new ActionSuccess(true);

		assertThat(actualActionSuccess).isEqualTo(expectedActionSuccess);
	}

}