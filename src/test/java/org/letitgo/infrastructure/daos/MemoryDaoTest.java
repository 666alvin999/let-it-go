package org.letitgo.infrastructure.daos;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.letitgo.domain.beans.ActionSuccess;
import org.letitgo.infrastructure.dtos.MemoryDTO;
import org.letitgo.utils.BasicDatabaseExtension;
import org.letitgo.utils.EzDatabase;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import static java.nio.file.Files.readAllBytes;
import static org.assertj.core.api.Assertions.assertThat;
import static org.letitgo.infrastructure.dtos.MemoryDTO.memoryDTO;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@ExtendWith(BasicDatabaseExtension.class)
class MemoryDaoTest {

	private MemoryDao memoryDao;

	@EzDatabase
	private NamedParameterJdbcTemplate jdbcTemplate;

	@BeforeEach
	public void setUp() {
		this.memoryDao = new MemoryDao();
		setField(this.memoryDao, "jdbcTemplate", this.jdbcTemplate);

		initTables();
	}

	@Test
	public void shouldSave() {
		// Arrange
		MemoryDTO memoryDTO = memoryDTO()
			.albumName("ahamaide's album")
			.username("ahamaide")
			.textContent("as;dlfj;aldjsf;")
			.memoryDatetime("2023-12-31:12-45-03")
			.mood("happy")
			.build();

		// Act
		ActionSuccess actualActionSuccess = this.memoryDao.save(memoryDTO);

		// Assert
		ActionSuccess expectedActionSuccess = new ActionSuccess(true);

		assertThat(actualActionSuccess).isEqualTo(expectedActionSuccess);
	}

	@Test
	public void shouldDelete() {
		// Arrange
		MemoryDTO memoryDTO = memoryDTO()
			.albumName("ahamaide's album")
			.username("ahamaide")
			.memoryDatetime("2024-12-12 12:12:12")
			.build();

		// Act
		ActionSuccess actualActionSuccess = this.memoryDao.delete(memoryDTO);

		// Assert
		ActionSuccess expectedActionSuccess = new ActionSuccess(true);

		assertThat(actualActionSuccess).isEqualTo(expectedActionSuccess);
	}

	@Test
	public void shouldGetMemoriesByUserNameAndAlbumName() {
	    // Act
		List<MemoryDTO> actualMemoryDTOs = this.memoryDao.getMemoriesByUsernameAndAlbumName("ahamaide", "ahamaide's album");

	    // Assert
	    List<MemoryDTO> expectedMemoryDTOs = List.of(
			memoryDTO()
				.username("ahamaide")
				.mediaName("test_img.png")
				.textContent("Je suis patate")
				.memoryDatetime("2024-01-01 12:12:12")
				.mood("happy")
				.albumName("ahamaide's album")
				.build(),
		    memoryDTO()
			    .username("ahamaide")
			    .textContent("Je suis AAAAAH")
			    .memoryDatetime("2024-01-01 12:12:13")
			    .mood("angry")
			    .albumName("ahamaide's album")
			    .build()
	    );

		assertThat(actualMemoryDTOs).isEqualTo(expectedMemoryDTOs);
	}

	@Test
	public void shouldGetMediaNamesByAlbumNameAndUsername() {
		// Arrange
		String albumName = "ahamaide's album";
		String username = "ahamaide";

		// Act
		List<MemoryDTO> actualMemoryDTO = this.memoryDao.getMediaNamesByAlbumNameAndUsername(albumName, username);

		// Assert
		List<MemoryDTO> expectedMemoryDTO = List.of(
			memoryDTO()
				.mediaName("test_img.png")
				.albumName("ahamaide's album")
				.username("ahamaide")
				.build()
		);

		assertThat(actualMemoryDTO).isEqualTo(expectedMemoryDTO);
	}

	@Test
	public void shouldGetDatesByUsername() {
		// Act
		Set<String> actualDates = this.memoryDao.getDatesByUsername("ahamaide");

		// Assert
		Set<String> expectedDates = Set.of("2024-01-01 12:12:12", "2024-01-01 12:12:13");

		assertThat(actualDates).isEqualTo(expectedDates);
	}

	@SneakyThrows
	private void initTables() {
		this.jdbcTemplate.update(
			new String(readAllBytes(Paths.get("src/test/resources/memory_init.sql"))),
			new HashMap<>()
		);
	}

}