package org.letitgo.infrastructure.daos;

import org.letitgo.domain.beans.ActionSuccess;
import org.letitgo.infrastructure.dtos.AlbumDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Component
public class AlbumDao {

	private NamedParameterJdbcTemplate jdbcTemplate;

	private final String SAVE = "INSERT INTO ALBUM VALUES (:albumName, :username)";
	private final String GET_BY_ALBUM_NAME_AND_USERNAME = "SELECT * FROM ALBUM WHERE ALBUM_NAME = :albumName AND USERNAME = :username";
	private final String GET_BY_USERNAME = "SELECT * FROM ALBUM WHERE USERNAME = :username";

	public AlbumDao() {
	}

	@Autowired
	public AlbumDao(@Qualifier(value = "dataSource") DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	public ActionSuccess save(AlbumDTO albumDTO) {
		if (!this.getAlbumByAlbumNameAndUsername(albumDTO).isEmpty()) {
			return new ActionSuccess(false, Optional.of("Un album avec ce nom existe déjà."));
		}

		try {
			Map<String, String> params = Map.of(
				"albumName", albumDTO.getAlbumName(),
				"username", albumDTO.getUsername()
			);

			this.jdbcTemplate.update(SAVE, params);

			return new ActionSuccess(true);
		} catch (Exception e) {
			return new ActionSuccess(false, Optional.ofNullable(e.getMessage()));
		}
	}

	public List<AlbumDTO> getAlbumsByUsername(String username) {
		Map<String, String> params = Map.of(
			"username", username
		);

		return this.jdbcTemplate.query(GET_BY_USERNAME, params, new BeanPropertyRowMapper<>(AlbumDTO.class));
	}

	public List<AlbumDTO> getAlbumByAlbumNameAndUsername(AlbumDTO albumDTO) {
		Map<String, String> params = Map.of(
			"albumName", albumDTO.getAlbumName(),
			"username", albumDTO.getUsername()
		);

		return this.jdbcTemplate.query(GET_BY_ALBUM_NAME_AND_USERNAME, params, new BeanPropertyRowMapper<>(AlbumDTO.class));
	}

}
