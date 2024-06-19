package org.letitgo.infrastructure.daos;

import org.letitgo.domain.beans.ActionSuccess;
import org.letitgo.infrastructure.dtos.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.Map;
import java.util.Optional;

@Component
public class UserDao {

	private NamedParameterJdbcTemplate jdbcTemplate;

	private final String REGISTER = "INSERT INTO USERS VALUES (:username, :birthDate, :identity, :password)";

	public UserDao() {
	}

	@Autowired
	public UserDao(@Qualifier(value = "dataSource") DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	public ActionSuccess register(UserDTO userDTO) {
		Map<String, String> params = Map.of(
			"username", userDTO.getUsername(),
			"birthDate", userDTO.getBirthDate(),
			"identity", userDTO.getIdentity(),
			"password", userDTO.getPassword()
		);

		try {
			this.jdbcTemplate.update(REGISTER, params);
			return new ActionSuccess(true);
		} catch (Exception e) {
			return new ActionSuccess(false, Optional.of(e.getMessage()));
		}
	}

	public UserDTO getUserByUsername(String username) {
		Map<String, String> params = Map.of("username", username);

//		try {
//			return this.jdbcTemplate.query()
//		}
	}

}
