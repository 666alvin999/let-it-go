package org.letitgo.infrastructure.adapters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.letitgo.domain.beans.ActionSuccess;
import org.letitgo.domain.beans.User;
import org.letitgo.domain.beans.userfields.BirthDate;
import org.letitgo.domain.beans.userfields.Identity;
import org.letitgo.domain.beans.userfields.Password;
import org.letitgo.domain.beans.userfields.Username;
import org.letitgo.infrastructure.daos.UserDao;
import org.letitgo.infrastructure.dtos.UserDTO;
import org.letitgo.infrastructure.mappers.UserMapper;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
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
			new BirthDate(LocalDate.of(2024, 1, 1)),
			Identity.HE,
			new Password("password")
		);

		UserDTO userDTO = userDTO()
			.username("ahamaide")
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

}