package org.letitgo.infrastructure.daos;

import org.letitgo.domain.beans.ActionSuccess;
import org.letitgo.infrastructure.dtos.MemoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.*;

import static java.util.Objects.nonNull;

@Component
public class MemoryDao {

	private NamedParameterJdbcTemplate jdbcTemplate;

	private final String SAVE_BEGINNING = "INSERT INTO MEMORY (ALBUM_NAME, USERNAME, MEMORY_DATETIME, MOOD";
	private final String DELETE = "DELETE FROM MEMORY WHERE ALBUM_NAME = :albumName AND USERNAME = :username AND MEMORY_DATETIME = :memoryDatetime";
	private final String GET_BY_USERNAME_AND_ALBUM_ORDER_BY_DATETIME = "SELECT * FROM MEMORY WHERE ALBUM_NAME = :albumName AND USERNAME = :username ORDER BY MEMORY_DATETIME ASC";
	private final String GET_BY_USERNAME_AND_DATETIME = "SELECT * FROM MEMORY WHERE USERNAME = :username AND MEMORY_DATETIME = :memoryDatetime";
	private final String GET_MEDIA_NAMES_BY_USERNAME_AND_ALBUM_NAME = "SELECT MEDIA_NAME, ALBUM_NAME, USERNAME FROM MEMORY WHERE ALBUM_NAME = :albumName AND USERNAME = :username AND MEDIA_NAME IS NOT NULL";
	private final String GET_DATES_BY_USERNAME = "SELECT MEMORY_DATETIME FROM MEMORY WHERE USERNAME = :username";

	public MemoryDao() {
	}

	@Autowired
	public MemoryDao(@Qualifier(value = "dataSource") DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	public ActionSuccess save(MemoryDTO memoryDTO) {
		try {
			if (!this.getMemoryByUsernameAndDatetime(memoryDTO.getUsername(), memoryDTO.getMemoryDatetime()).isEmpty()) {
				return new ActionSuccess(false, Optional.of("Un souvenir existe déjà pour cet instant"));
			}

			String query = this.SAVE_BEGINNING;

			HashMap<String, String> params = new HashMap<>();

			params.put("albumName", memoryDTO.getAlbumName());
			params.put("username", memoryDTO.getUsername());
			params.put("memoryDatetime", memoryDTO.getMemoryDatetime());
			params.put("mood", memoryDTO.getMood());

			if (nonNull(memoryDTO.getTextContent())) {
				params.put("textContent", memoryDTO.getTextContent());
				query += ", TEXT_CONTENT";
			}

			if (nonNull(memoryDTO.getMediaName())) {
				params.put("mediaName", memoryDTO.getMediaName());
				query += ", MEDIA_NAME";
			}

			query += ") VALUES (:albumName, :username, :memoryDatetime, :mood";

			if (nonNull(memoryDTO.getTextContent())) {
				query += ", :textContent";
			}

			if (nonNull(memoryDTO.getMediaName())) {
				query += ", :mediaName";
			}

			query += ")";

			this.jdbcTemplate.update(query, params);

			return new ActionSuccess(true);
		} catch (Exception e) {
			return new ActionSuccess(false, Optional.ofNullable(e.getMessage()));
		}
	}

	public ActionSuccess delete(MemoryDTO memoryDTO) {
		try {
			Map<String, String> params = Map.of(
				"albumName", memoryDTO.getAlbumName(),
				"username", memoryDTO.getUsername(),
				"memoryDatetime", memoryDTO.getMemoryDatetime()
			);

			this.jdbcTemplate.update(DELETE, params);

			return new ActionSuccess(true);
		} catch (Exception e) {
			return new ActionSuccess(false, Optional.ofNullable(e.getMessage()));
		}
	}

	public List<MemoryDTO> getMemoriesByUsernameAndAlbumName(String username, String albumName) {
		Map<String, String> params = Map.of(
			"username", username,
			"albumName", albumName
		);

		return this.jdbcTemplate.query(GET_BY_USERNAME_AND_ALBUM_ORDER_BY_DATETIME, params, new BeanPropertyRowMapper<>(MemoryDTO.class));
	}

	public List<MemoryDTO> getMediaNamesByAlbumNameAndUsername(String albumName, String username) {
		Map<String, String> params = Map.of(
			"albumName", albumName,
			"username", username
		);

		return this.jdbcTemplate.query(GET_MEDIA_NAMES_BY_USERNAME_AND_ALBUM_NAME, params, new BeanPropertyRowMapper<>(MemoryDTO.class));
	}

	public Set<String> getDatesByUsername(String username) {
		Map<String, String> params = Map.of(
			"username", username
		);

		return new HashSet<>(this.jdbcTemplate.queryForList(GET_DATES_BY_USERNAME, params, String.class));
	}

	private List<MemoryDTO> getMemoryByUsernameAndDatetime(String username, String memoryDatetime) {
		Map<String, String> params = Map.of(
			"username", username,
			"memoryDatetime", memoryDatetime
		);

		return this.jdbcTemplate.query(GET_BY_USERNAME_AND_DATETIME, params, new BeanPropertyRowMapper<>(MemoryDTO.class));
	}

}
