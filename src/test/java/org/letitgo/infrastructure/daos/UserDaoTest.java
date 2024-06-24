package org.letitgo.infrastructure.daos;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.letitgo.domain.beans.ActionSuccess;
import org.letitgo.infrastructure.dtos.UserDTO;
import org.letitgo.utils.BasicDatabaseExtension;
import org.letitgo.utils.EzDatabase;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

import static java.nio.file.Files.readAllBytes;
import static org.assertj.core.api.Assertions.assertThat;
import static org.letitgo.infrastructure.dtos.UserDTO.userDTO;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@ExtendWith({BasicDatabaseExtension.class, MockitoExtension.class})
class UserDaoTest {

	private UserDao userDao;

	@EzDatabase
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Mock
	private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@BeforeEach
	public void setUp() {
		this.userDao = new UserDao();
		setField(this.userDao, "jdbcTemplate", this.jdbcTemplate);
		setField(this.userDao, "passwordEncoder", this.passwordEncoder);

		initTables();
	}

	@Test
	public void shouldRegisterUser() {
		// Arrange
		UserDTO userDTO = userDTO()
			.username("macret")
			.mail("mail2")
			.birthDate("2024-01-01")
			.userIdentity("SHE")
			.pwd("password")
			.build();

		when(this.passwordEncoder.encode("password")).thenReturn("encoded");

		// Act
		this.userDao.register(userDTO);

		// Assert
		UserDTO expectedUserDTO = userDTO()
			.username("macret")
			.mail("mail2")
			.birthDate("2024-01-01")
			.userIdentity("SHE")
			.pwd("encoded")
			.build();

		assertThat(this.userDao.getUserByUsername("macret")).isEqualTo(List.of(expectedUserDTO));
	}

	@Test
	public void shouldNotRegisterUser() {
		// Arrange
		UserDTO userDTO = userDTO()
			.username("ahamaide")
			.mail("mail2")
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
	public void shouldLogUserInByUsername() {
		// Arrange
		UserDTO userDTO = userDTO()
			.username("ahamaide")
			.pwd("password")
			.build();

		when(this.passwordEncoder.matches("password", "password")).thenReturn(true);

		// Act
		ActionSuccess actualActionSuccess = this.userDao.logUserIn(userDTO);

		// Assert
		ActionSuccess expectedActionSuccess = new ActionSuccess(true);

		assertThat(actualActionSuccess).isEqualTo(expectedActionSuccess);
	}

	@Test
	public void shouldLogUserInByMail() {
		// Arrange
		UserDTO userDTO = userDTO()
			.mail("mail")
			.pwd("password")
			.build();

		when(this.passwordEncoder.matches("password", "password")).thenReturn(true);

		// Act
		ActionSuccess actualActionSuccess = this.userDao.logUserIn(userDTO);

		// Assert
		ActionSuccess expectedActionSuccess = new ActionSuccess(true);

		assertThat(actualActionSuccess).isEqualTo(expectedActionSuccess);
	}

	@Test
	public void shouldNotLogUserInByUsername() {
		// Arrange
		UserDTO userDTO = userDTO()
			.username("ahamaide")
			.pwd("password")
			.build();

		when(this.passwordEncoder.matches("password", "password")).thenReturn(false);

		// Act
		ActionSuccess actualActionSuccess = this.userDao.logUserIn(userDTO);

		// Assert
		ActionSuccess expectedActionSuccess = new ActionSuccess(false, Optional.of("Les informations de connexion sont invalides"));

		assertThat(actualActionSuccess).isEqualTo(expectedActionSuccess);
	}

	@Test
	public void shouldNotLogUserInByMail() {
		// Arrange
		UserDTO userDTO = userDTO()
			.mail("mail")
			.pwd("password")
			.build();

		when(this.passwordEncoder.matches("password", "password")).thenReturn(false);

		// Act
		ActionSuccess actualActionSuccess = this.userDao.logUserIn(userDTO);

		// Assert
		ActionSuccess expectedActionSuccess = new ActionSuccess(false, Optional.of("Les informations de connexion sont invalides"));

		assertThat(actualActionSuccess).isEqualTo(expectedActionSuccess);
	}

	@Test
	public void shouldNotFindUserByUsername() {
		// Arrange
		UserDTO userDTO = userDTO()
			.username("macret")
			.pwd("password")
			.build();

		// Act
		ActionSuccess actualActionSuccess = this.userDao.logUserIn(userDTO);

		// Assert
		ActionSuccess expectedActionSuccess = new ActionSuccess(false, Optional.of("Utilisateur introuvable"));

		assertThat(actualActionSuccess).isEqualTo(expectedActionSuccess);
	}

	@Test
	public void shouldNotFindUserByMail() {
		// Arrange
		UserDTO userDTO = userDTO()
			.mail("mail2")
			.pwd("password")
			.build();

		// Act
		ActionSuccess actualActionSuccess = this.userDao.logUserIn(userDTO);

		// Assert
		ActionSuccess expectedActionSuccess = new ActionSuccess(false, Optional.of("Utilisateur introuvable"));

		assertThat(actualActionSuccess).isEqualTo(expectedActionSuccess);
	}

	@Test
	public void shouldReturnUserDTOByUsername() {
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

	@Test
	public void shouldReturnUserDTOByMail() {
		// Act
		List<UserDTO> actualUserDTO = this.userDao.getUserByMail("mail");

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