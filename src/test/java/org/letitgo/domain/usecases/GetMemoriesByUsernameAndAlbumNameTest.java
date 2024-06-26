package org.letitgo.domain.usecases;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.letitgo.domain.beans.Memory;
import org.letitgo.domain.beans.albumfields.AlbumName;
import org.letitgo.domain.beans.memoryfields.Content;
import org.letitgo.domain.beans.memoryfields.MediaName;
import org.letitgo.domain.beans.memoryfields.MemoryDatetime;
import org.letitgo.domain.beans.memoryfields.Mood;
import org.letitgo.domain.beans.userfields.Username;
import org.letitgo.domain.ports.MemoryPort;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetMemoriesByUsernameAndAlbumNameTest {

	private GetMemoriesByUsernameAndAlbumName usecase;

	@Mock
	private MemoryPort memoryPort;

	@BeforeEach
	public void setUp() {
		this.usecase = new GetMemoriesByUsernameAndAlbumName(memoryPort);
	}

	@Test
	public void shouldGetMemoriesByUsernameAndAlbumName() {
	    // Arrange
		String username = "username";
		String albumName = "albumName";

		List<Memory> memories = List.of(
			new Memory(
				new AlbumName("album"),
				new Username("username"),
				new Content("content"),
				new MediaName("mediaName"),
				new MemoryDatetime(LocalDateTime.of(2024, 1, 1, 12, 12, 12)),
				Mood.HAPPY
			)
		);

		when(this.memoryPort.getMemoriesByUsernameAndAlbumName(username, albumName)).thenReturn(memories);

	    // Act
		List<Memory> actualMemories = this.usecase.execute(username, albumName);

	    // Assert
	    List<Memory> expectedMemories = List.of(
		    new Memory(
			    new AlbumName("album"),
			    new Username("username"),
			    new Content("content"),
			    new MediaName("mediaName"),
			    new MemoryDatetime(LocalDateTime.of(2024, 1, 1, 12, 12, 12)),
			    Mood.HAPPY
		    )
	    );

		assertThat(actualMemories).isEqualTo(expectedMemories);
	}

}