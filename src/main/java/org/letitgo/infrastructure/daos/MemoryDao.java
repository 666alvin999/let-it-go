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

	private final String SAVE_BEGINNING = "INSERT INTO MEMORY (ALBUM_NAME, USERNAME";
	private final String DELETE = "DELETE FROM MEMORY WHERE ALBUM_NAME = :albumName AND USERNAME = :username AND MEMORY_DATETIME = :memoryDatetime";
	private final String GET_BY_USERNAME_AND_DATETIME = "SELECT * FROM MEMORY WHERE USERNAME = :username AND MEMORY_DATETIME = :memoryDatetime";
	private final String GET_MEDIA_NAMES_BY_USERNAME_AND_ALBUM_NAME = "SELECT MEDIA_NAME FROM MEMORY WHERE ALBUM_NAME = :albumName AND USERNAME = :username AND MEDIA_NAME IS NOT NULL";
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

			if (nonNull(memoryDTO.getTextContent())) {
				params.put("textContent", memoryDTO.getTextContent());
				query += ", TEXT_CONTENT";
			}

			query += ", MEMORY_DATETIME";

			if (nonNull(memoryDTO.getMediaName())) {
				params.put("mediaName", memoryDTO.getMediaName());
				query += ", MEDIA_NAME";
			}

			query += ") VALUES (:albumName, :username";

			if (nonNull(memoryDTO.getTextContent())) {
				query += ", :textContent";
			}

			query += ", :memoryDatetime";

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

	public List<String> getMediaNamesByAlbumNameAndUsername(String albumName, String username) {
		Map<String, String> params = Map.of(
			"albumName", albumName,
			"username", username
		);

		return this.jdbcTemplate.queryForList(GET_MEDIA_NAMES_BY_USERNAME_AND_ALBUM_NAME, params, String.class);
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
