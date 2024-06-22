package org.letitgo.infrastructure.daos;

import org.letitgo.domain.beans.ActionSuccess;
import org.letitgo.infrastructure.dtos.MemoryDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Objects.nonNull;

@Component
public class MemoryDao {

	private NamedParameterJdbcTemplate jdbcTemplate;

	private final String GET_BY_USERNAME_AND_DATETIME = "SELECT * FROM MEMORY WHERE USERNAME = :username AND MEMORY_DATETIME = :memoryDatetime";
	private final String SAVE_BEGINNING = "INSERT INTO MEMORY (USERNAME, TEXT_CONTENT, MEMORY_DATETIME";

	public MemoryDao() {
	}

	@Autowired
	public MemoryDao(@Qualifier(value = "dataSource") DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	public ActionSuccess saveMemory(MemoryDTO memoryDTO) {
		try {
			if (!this.getMemoryByUsernameAndDatetime(memoryDTO.getUsername(), memoryDTO.getMemoryDatetime()).isEmpty()) {
				return new ActionSuccess(false, Optional.of("Un souvenir existe déjà pour cet instant"));
			}

			String query = this.SAVE_BEGINNING;

			HashMap<String, String> params = new HashMap<>();

			params.put("username", memoryDTO.getUsername());
			params.put("textContent", memoryDTO.getTextContent());
			params.put("memoryDatetime", memoryDTO.getMemoryDatetime());

			if (nonNull(memoryDTO.getMediaName())) {
				params.put("mediaName", memoryDTO.getMediaName());
				query += ", MEDIA_NAME";
			}

			query += ") VALUES (:username, :textContent, :memoryDatetime";

			if (nonNull(memoryDTO.getMediaName())) {
				query += ", :mediaName";
			}

			query += ");";

			this.jdbcTemplate.update(query, params);

			return new ActionSuccess(true);
		} catch (Exception e) {
			return new ActionSuccess(false, Optional.ofNullable(e.getMessage()));
		}
	}

	private List<MemoryDTO> getMemoryByUsernameAndDatetime(String username, String memoryDatetime) {
		Map<String, String> params = Map.of(
			"username", username,
			"memoryDatetime", memoryDatetime
		);

		return this.jdbcTemplate.query(GET_BY_USERNAME_AND_DATETIME, params, new BeanPropertyRowMapper<>(MemoryDTO.class));
	}

}
