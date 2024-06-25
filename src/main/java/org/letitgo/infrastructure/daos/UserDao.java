package org.letitgo.infrastructure.daos;

import org.letitgo.domain.beans.ActionSuccess;
import org.letitgo.infrastructure.dtos.UserDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.util.Objects.nonNull;

@Component
public class UserDao {

	private NamedParameterJdbcTemplate jdbcTemplate;

	private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	private final String REGISTER = "INSERT INTO USERS (USERNAME, MAIL, BIRTH_DATE, USER_IDENTITY, PWD, COLOR_THEME) VALUES (:username, :mail, :birthDate, :identity, :password, :colorTheme)";
	private final String GET_BY_USERNAME = "SELECT * FROM USERS WHERE USERNAME = :username";
	private final String GET_BY_MAIL = "SELECT * FROM USERS WHERE MAIL = :mail";

	public UserDao() {
	}

	@Autowired
	public UserDao(@Qualifier(value = "dataSource") DataSource dataSource) {
		this.jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	}

	public ActionSuccess register(UserDTO userDTO) {
		if (this.getUserByUsername(userDTO.getUsername()).isEmpty() && this.getUserByMail(userDTO.getMail()).isEmpty()) {
			try {
				Map<String, String> params = Map.of(
					"username", userDTO.getUsername(),
					"mail", userDTO.getMail(),
					"birthDate", userDTO.getBirthDate(),
					"identity", userDTO.getUserIdentity(),
					"password", this.passwordEncoder.encode(userDTO.getPwd()),
					"colorTheme", userDTO.getColorTheme()
				);

				this.jdbcTemplate.update(REGISTER, params);
				return new ActionSuccess(true);
			} catch (Exception e) {
				if (e.getClass() == NullPointerException.class) {
					return new ActionSuccess(false, Optional.of("Un des champs est vide."));
				}

				return new ActionSuccess(false, Optional.ofNullable(e.getMessage()));
			}
		} else if (!this.getUserByMail(userDTO.getMail()).isEmpty()) {
			return new ActionSuccess(false, Optional.of("Ce mail est déjà pris."));
		} else {
			return new ActionSuccess(false, Optional.of("L'utilisateur existe déjà."));
		}
	}

	public ActionSuccess logUserIn(UserDTO userDTO) {
		if (nonNull(userDTO.getUsername())) {
			List<UserDTO> fetchedUserDTO = this.getUserByUsername(userDTO.getUsername());

			if (fetchedUserDTO.isEmpty()) {
				return new ActionSuccess(false, Optional.of("Utilisateur introuvable"));
			}

			if (this.passwordEncoder.matches(userDTO.getPwd(), fetchedUserDTO.getFirst().getPwd())) {
				return new ActionSuccess(true);
			}
		}

		if (nonNull(userDTO.getMail())) {
			List<UserDTO> fetchedUserDTO = this.getUserByMail(userDTO.getMail());

			if (fetchedUserDTO.isEmpty()) {
				return new ActionSuccess(false, Optional.of("Utilisateur introuvable"));
			}

			if (this.passwordEncoder.matches(userDTO.getPwd(), fetchedUserDTO.getFirst().getPwd())) {
				return new ActionSuccess(true);
			}
		}

		return new ActionSuccess(false, Optional.of("Les informations de connexion sont invalides"));
	}

	public List<UserDTO> getUserByUsername(String username) {
		Map<String, String> params = Map.of("username", username);

		return this.jdbcTemplate.query(GET_BY_USERNAME, params, new BeanPropertyRowMapper<>(UserDTO.class));
	}

	public List<UserDTO> getUserByMail(String mail) {
		Map<String, String> params = Map.of("mail", mail);

		return this.jdbcTemplate.query(GET_BY_MAIL, params, new BeanPropertyRowMapper<>(UserDTO.class));
	}

	public ActionSuccess isUsernameFree(String username) {
		if (this.getUserByUsername(username).isEmpty()) {
			return new ActionSuccess(true);
		}

		return new ActionSuccess(false, Optional.of("Ce nom d'utilisateur est déjà pris"));
	}

	public ActionSuccess isMailFree(String mail) {
		if (this.getUserByMail(mail).isEmpty()) {
			return new ActionSuccess(true);
		}

		return new ActionSuccess(false, Optional.of("Ce mail est déjà pris"));
	}
}
