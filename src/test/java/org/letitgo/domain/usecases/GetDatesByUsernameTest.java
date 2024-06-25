package org.letitgo.domain.usecases;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.letitgo.domain.ports.MemoryPort;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class GetDatesByUsernameTest {

	private GetDatesByUsername getDatesByUsername;

	@Mock
	private MemoryPort memoryPort;

	@BeforeEach
	public void setUp() {
		this.getDatesByUsername = new GetDatesByUsername(memoryPort);
	}

	@Test
	public void shouldReturnDatesByUsername() {
		// Arrange
		when(this.memoryPort.getDatesByUsername("ahamaide")).thenReturn(this.getDates());

		// Act
		Set<LocalDate> actualAlbums = this.getDatesByUsername.execute("ahamaide");

		// Assert
		Set<LocalDate> expectedAlbums = this.getDates();

		assertThat(actualAlbums).isEqualTo(expectedAlbums);
	}

	private Set<LocalDate> getDates() {
		return Set.of(LocalDate.of(2024, 1, 1), LocalDate.of(2023, 1, 1));
	}

}