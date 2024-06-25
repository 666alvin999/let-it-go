package org.letitgo.infrastructure.adapters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.letitgo.domain.beans.ActionSuccess;
import org.letitgo.domain.beans.User;
import org.letitgo.domain.beans.userfields.*;
import org.letitgo.infrastructure.daos.UserDao;
import org.letitgo.infrastructure.dtos.UserDTO;
import org.letitgo.infrastructure.mappers.UserMapper;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.letitgo.infrastructure.dtos.UserDTO.userDTO;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserAdapterTest {

	private UserAdapter userAdapter;

	@Mock
	private UserMapper userMapper;

	@Mock
	private UserDao userDao;

	@BeforeEach
	public void setUp() {
		this.userAdapter = new UserAdapter(userDao, userMapper);
	}

	@Test
	public void shouldRegisterSuccessfully() {
		// Arrange
		User user = new User(
			new Username("ahamaide"),
			new Mail("mail"),
			new BirthDate(LocalDate.of(2024, 1, 1)),
			Identity.HE,
			new Password("password")
		);

		UserDTO userDTO = userDTO()
			.username("ahamaide")
			.mail("mail")
			.birthDate("2024-01-01")
			.userIdentity("HE")
			.pwd("password")
			.build();

		when(this.userMapper.mapToDTO(user)).thenReturn(userDTO);
		when(this.userDao.register(userDTO)).thenReturn(new ActionSuccess(true));

		// Act
		ActionSuccess actualActionSuccess = this.userAdapter.register(user);

		// Assert
		ActionSuccess expectedActionSuccess = new ActionSuccess(true);

		assertThat(actualActionSuccess).isEqualTo(expectedActionSuccess);
	}

	@Test
	public void shouldLogUserInSuccessfully() {
		// Arrange
		User user = new User(
			new Username("ahamaide"),
			new Mail(null),
			new BirthDate(null),
			null,
			new Password("password")
		);

		UserDTO userDTO = userDTO()
			.username("ahamaide")
			.pwd("password")
			.build();

		when(this.userMapper.mapToDTO(user)).thenReturn(userDTO);
		when(this.userDao.logUserIn(userDTO)).thenReturn(new ActionSuccess(true));

		// Act
		ActionSuccess actualActionSuccess = this.userAdapter.logUserIn(user);

		// Assert
		ActionSuccess expectedActionSuccess = new ActionSuccess(true);

		assertThat(actualActionSuccess).isEqualTo(expectedActionSuccess);
	}

	@Test
	public void shouldLogUserInSuccessfully_whenMailIsGiven() {
		// Arrange
		User user = new User(
			new Username(null),
			new Mail("mail"),
			new BirthDate(null),
			null,
			new Password("password")
		);

		UserDTO userDTO = userDTO()
			.mail("mail")
			.pwd("password")
			.build();

		when(this.userMapper.mapToDTO(user)).thenReturn(userDTO);
		when(this.userDao.logUserIn(userDTO)).thenReturn(new ActionSuccess(true));

		// Act
		ActionSuccess actualActionSuccess = this.userAdapter.logUserIn(user);

		// Assert
		ActionSuccess expectedActionSuccess = new ActionSuccess(true);

		assertThat(actualActionSuccess).isEqualTo(expectedActionSuccess);
	}

	@Test
	public void shouldReturnUsernameIsFree() {
	    // Arrange
	    String username = "ahamaide";

		when(this.userDao.isUsernameFree(username)).thenReturn(new ActionSuccess(true));

	    // Act
		ActionSuccess actualActionSuccess = this.userAdapter.isUsernameFree(username);

	    // Assert
		ActionSuccess expectedActionSuccess = new ActionSuccess(true);

		assertThat(actualActionSuccess).isEqualTo(expectedActionSuccess);
	}

	@Test
	public void shouldReturnMailIsFree() {
		// Arrange
		String mail = "mail";

		when(this.userDao.isMailFree(mail)).thenReturn(new ActionSuccess(true));

		// Act
		ActionSuccess actualActionSuccess = this.userAdapter.isMailFree(mail);

		// Assert
		ActionSuccess expectedActionSuccess = new ActionSuccess(true);

		assertThat(actualActionSuccess).isEqualTo(expectedActionSuccess);
	}

}