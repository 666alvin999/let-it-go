package org.letitgo.domain.usecases;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.letitgo.domain.beans.ActionSuccess;
import org.letitgo.domain.beans.Memory;
import org.letitgo.domain.beans.albumfields.AlbumName;
import org.letitgo.domain.beans.memoryfields.*;
import org.letitgo.domain.beans.userfields.Username;
import org.letitgo.domain.ports.MemoryPort;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeleteMemoryTest {

	private DeleteMemory deleteMemory;

	@Mock
	private MemoryPort memoryPort;

	@BeforeEach
	public void setUp() {
		this.deleteMemory = new DeleteMemory(memoryPort);
	}

	@Test
	public void shouldDeleteMemorySuccessfully() {
		// Arrange
		Memory memory = new Memory(
			new AlbumName("ahamaide's album"),
			new Username("ahamaide"),
			new Content("salut c'est cool"),
			new MediaName("test_img.jpg"),
			new MemoryDatetime(LocalDateTime.of(2024, 1, 1, 12, 12, 12)),
			Mood.HAPPY
		);

		when(this.memoryPort.delete(memory)).thenReturn(new ActionSuccess(true));

		// Act
		ActionSuccess actualActionSuccess = this.deleteMemory.execute(memory);

		// Assert
		ActionSuccess expectedMemory = new ActionSuccess(true);

		assertThat(actualActionSuccess).isEqualTo(expectedMemory);
	}

}