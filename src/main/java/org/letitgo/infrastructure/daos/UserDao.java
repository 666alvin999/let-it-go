package org.letitgo.infrastructure.daos;

import org.letitgo.domain.beans.ActionSuccess;
import org.letitgo.infrastructure.dtos.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Collections.emptyList;

@Component
public class UserDao {

	private NamedParameterJdbcTemplate jdbcTemplate;

	private final String REGISTER = "INSERT INTO USERS VALUES (:username, :mail, :birthDate, :identity, :password)";
	private final String GET_BY_USERNAME = "SELECT * FROM USERS WHERE USERNAME = :username";

	public UserDao() {
	}

	@Autowired
	public UserDao(@Qualifier(value = "dataSource") DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	public ActionSuccess register(UserDTO userDTO) {
		if (this.getUserByUsername(userDTO.getUsername()).isEmpty()) {
			Map<String, String> params = Map.of(
				"username", userDTO.getUsername(),
				"mail", userDTO.getMail(),
				"birthDate", userDTO.getBirthDate(),
				"identity", userDTO.getUserIdentity(),
				"password", userDTO.getPwd()
			);

			try {
				this.jdbcTemplate.update(REGISTER, params);
				return new ActionSuccess(true);
			} catch (Exception e) {
				return new ActionSuccess(false, Optional.of(e.getMessage()));
			}
		} else {
			return new ActionSuccess(false, Optional.of("L'utilisateur existe déjà."));
		}
	}

	public List<UserDTO> getUserByUsername(String username) {
		Map<String, String> params = Map.of("username", username);

		return this.jdbcTemplate.query(GET_BY_USERNAME, params, new BeanPropertyRowMapper<>(UserDTO.class));
	}

}
