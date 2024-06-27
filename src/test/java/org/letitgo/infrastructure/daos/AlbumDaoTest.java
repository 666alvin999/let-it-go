package org.letitgo.infrastructure.daos;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.letitgo.domain.beans.ActionSuccess;
import org.letitgo.infrastructure.dtos.AlbumDTO;
import org.letitgo.utils.BasicDatabaseExtension;
import org.letitgo.utils.EzDatabase;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;

import static java.nio.file.Files.readAllBytes;
import static org.assertj.core.api.Assertions.assertThat;
import static org.letitgo.infrastructure.dtos.AlbumDTO.albumDTO;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@ExtendWith(BasicDatabaseExtension.class)
class AlbumDaoTest {

	private AlbumDao albumDao;

	@EzDatabase
	private NamedParameterJdbcTemplate jdbcTemplate;

	@BeforeEach
	public void setUp() {
		this.albumDao = new AlbumDao();
		setField(this.albumDao, "jdbcTemplate", this.jdbcTemplate);

		initTables();
	}

	@Test
	public void shouldSaveAlbum() {
		// Arrange
		AlbumDTO albumDTO = albumDTO()
			.albumName("album2")
			.username("ahamaide")
			.build();

		// Act
		ActionSuccess actualActionSuccess = this.albumDao.save(albumDTO);

		// Assert
		ActionSuccess expectedActionSuccess = new ActionSuccess(true);

		assertThat(actualActionSuccess).isEqualTo(expectedActionSuccess);
	}

	@Test
	public void shouldDeleteAlbum() {
		// Arrange
		AlbumDTO albumDTO = albumDTO()
			.albumName("trichier's album")
			.username("trichier")
			.build();

		// Act
		ActionSuccess actualActionSuccess = this.albumDao.delete(albumDTO);

		// Assert
		ActionSuccess expectedActionSuccess = new ActionSuccess(true);

		assertThat(actualActionSuccess).isEqualTo(expectedActionSuccess);
	}

	@Test
	public void shouldGetAlbumByNameAndUsername() {
		// Arrange
		AlbumDTO albumDTO = albumDTO()
			.albumName("ahamaide's album")
			.username("ahamaide")
			.build();

		// Act
		List<AlbumDTO> actualAlbumDTO = this.albumDao.getAlbumByAlbumNameAndUsername(albumDTO);

		// Assert
		List<AlbumDTO> expectedAlbumDTO = List.of(
			albumDTO()
				.albumName("ahamaide's album")
				.username("ahamaide")
				.build()
		);

		assertThat(actualAlbumDTO).isEqualTo(expectedAlbumDTO);
	}

	@Test
	public void shouldGetAlbumByUsername() {
		// Act
		List<AlbumDTO> actualAlbumDTO = this.albumDao.getAlbumsByUsername("ahamaide");

		// Assert
		List<AlbumDTO> expectedAlbumDTO = List.of(
			albumDTO()
				.albumName("ahamaide's album")
				.username("ahamaide")
				.build(),
			albumDTO()
				.albumName("album4")
				.username("ahamaide")
				.build()
		);

		assertThat(actualAlbumDTO).isEqualTo(expectedAlbumDTO);
	}

	@Test
	public void shouldGetAlbumCountByUsername() {
	    // Act
	    int actualAlbumCount = this.albumDao.getAlbumCountByUsername("ahamaide");

	    // Assert
	    int expectedAlbumCount = 2;

		assertThat(actualAlbumCount).isEqualTo(expectedAlbumCount);
	}

	@SneakyThrows
	private void initTables() {
		this.jdbcTemplate.update(
			new String(readAllBytes(Paths.get("src/test/resources/album_init.sql"))),
			new HashMap<>()
		);
	}

}