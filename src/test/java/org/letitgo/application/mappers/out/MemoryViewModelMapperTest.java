package org.letitgo.application.mappers.out;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.letitgo.application.dtos.out.MemoryViewModel;
import org.letitgo.domain.beans.Memory;
import org.letitgo.domain.beans.albumfields.AlbumName;
import org.letitgo.domain.beans.memoryfields.Content;
import org.letitgo.domain.beans.memoryfields.MediaName;
import org.letitgo.domain.beans.memoryfields.MemoryDatetime;
import org.letitgo.domain.beans.memoryfields.Mood;
import org.letitgo.domain.beans.userfields.Username;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

class MemoryViewModelMapperTest {

	private MemoryViewModelMapper mapper;

	@BeforeEach
	public void setUp() {
		this.mapper = new MemoryViewModelMapper();
	}

	@Test
	public void shouldMapToViewModel() {
		// Arrange
		Memory memory = new Memory(
			new AlbumName("album"),
			new Username("username"),
			new Content("content"),
			new MediaName(null),
			new MemoryDatetime(LocalDateTime.of(2024, 1, 1, 12, 12, 12)),
			Mood.HAPPY
		);

		// Act
		MemoryViewModel actualViewModel = this.mapper.mapToViewModel(memory);

		// Assert
		MemoryViewModel expectedViewModel = new MemoryViewModel(
			"content",
			"2024-01-01 12:12:12",
			null,
			"happy"
		);

		assertThat(actualViewModel).isEqualTo(expectedViewModel);
	}

}