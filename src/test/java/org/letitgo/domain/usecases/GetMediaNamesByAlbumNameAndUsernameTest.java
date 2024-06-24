package org.letitgo.domain.usecases;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.letitgo.domain.ports.MemoryPort;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetMediaNamesByAlbumNameAndUsernameTest {

	private GetMediaNamesByAlbumNameAndUsername getMediaNamesByAlbumNameAndUsername;

	@Mock
	private MemoryPort memoryPort;

	@BeforeEach
	public void setUp() {
		this.getMediaNamesByAlbumNameAndUsername = new GetMediaNamesByAlbumNameAndUsername(memoryPort);
	}

	@Test
	public void shouldGetMediaNamesByAlbumNameAndUsername() {
		// Arrange
		String albumName = "ahamaide's album";
		String username = "ahamaide";
		List<String> mediaNames = List.of("test_img.png");

		when(this.memoryPort.getMediaNamesByAlbumNameAndUsername(albumName, username)).thenReturn(mediaNames);

		// Act
		List<String> actualMediaNames = this.getMediaNamesByAlbumNameAndUsername.execute(albumName, username);

		// Assert
		List<String> expectedMediaNames = List.of("test_img.png");

		assertThat(actualMediaNames).isEqualTo(expectedMediaNames);
	}

}