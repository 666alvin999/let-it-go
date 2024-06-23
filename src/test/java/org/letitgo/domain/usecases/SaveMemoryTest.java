package org.letitgo.domain.usecases;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.letitgo.domain.beans.ActionSuccess;
import org.letitgo.domain.beans.Memory;
import org.letitgo.domain.beans.memoryfields.AlbumName;
import org.letitgo.domain.beans.memoryfields.Content;
import org.letitgo.domain.beans.memoryfields.MediaName;
import org.letitgo.domain.beans.memoryfields.MemoryDatetime;
import org.letitgo.domain.beans.userfields.Username;
import org.letitgo.domain.ports.MemoryPort;
import org.letitgo.infrastructure.dtos.MemoryDTO;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.letitgo.infrastructure.dtos.MemoryDTO.memoryDTO;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class SaveMemoryTest {

	private SaveMemory saveMemory;

	@Mock
	private MemoryPort memoryPort;

	@BeforeEach
	public void setUp() {
		this.saveMemory = new SaveMemory(memoryPort);
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

		when(this.memoryPort.save(memory)).thenReturn(new ActionSuccess(true));

		// Act
		ActionSuccess actualActionSuccess = this.saveMemory.execute(memory);

		// Assert
		ActionSuccess expectedMemory = new ActionSuccess(true);

		assertThat(actualActionSuccess).isEqualTo(expectedMemory);
	}

}