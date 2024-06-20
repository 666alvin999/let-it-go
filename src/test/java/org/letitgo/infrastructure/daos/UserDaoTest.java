package org.letitgo.infrastructure.daos;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.letitgo.domain.beans.ActionSuccess;
import org.letitgo.infrastructure.dtos.UserDTO;
import org.letitgo.utils.BasicDatabaseExtension;
import org.letitgo.utils.EzDatabase;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static java.nio.file.Files.readAllBytes;
import static org.assertj.core.api.Assertions.assertThat;
import static org.letitgo.infrastructure.dtos.UserDTO.userDTO;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@ExtendWith(BasicDatabaseExtension.class)
class UserDaoTest {

	private UserDao userDao;

	@EzDatabase
	private NamedParameterJdbcTemplate jdbcTemplate;

	@BeforeEach
	public void setUp() {
		this.userDao = new UserDao();
		setField(this.userDao, "jdbcTemplate", this.jdbcTemplate);

		initTables();
	}

	@Test
	public void shouldRegisterUser() {
	    // Arrange
		UserDTO userDTO = userDTO()
			.username("macret")
			.mail("mail")
			.birthDate("2024-01-01")
			.userIdentity("SHE")
			.pwd("password")
			.build();

	    // Act
		this.userDao.register(userDTO);

	    // Assert
		assertThat(this.userDao.getUserByUsername("macret")).isEqualTo(List.of(userDTO));
	}

	@Test
	public void shouldNotRegisterUser() {
		// Arrange
		UserDTO userDTO = userDTO()
			.username("ahamaide")
			.mail("mail")
			.birthDate("2024-01-01")
			.userIdentity("HE")
			.pwd("password")
			.build();

		// Act
		ActionSuccess actualActionSuccess = this.userDao.register(userDTO);

		// Assert
		ActionSuccess expectedActionSuccess = new ActionSuccess(false, Optional.of("L'utilisateur existe déjà."));

		assertThat(actualActionSuccess).isEqualTo(expectedActionSuccess);
	}

	@Test
	public void shouldReturnUserDTO() {
	    // Act
	    List<UserDTO> actualUserDTO = this.userDao.getUserByUsername("ahamaide");

		// Assert
	    List<UserDTO> expectedUserDTO = List.of(
			userDTO()
				.username("ahamaide")
				.mail("mail")
				.birthDate("2024-01-01")
				.userIdentity("HE")
				.pwd("password")
				.build()
	    );

		assertThat(actualUserDTO).isEqualTo(expectedUserDTO);
	}

	@SneakyThrows
	private void initTables() {
		this.jdbcTemplate.update(
			new String(readAllBytes(Paths.get("src/test/resources/user_init.sql"))),
			new HashMap<>()
		);
	}

}