package org.letitgo.domain.usecases;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.letitgo.domain.beans.User;
import org.letitgo.domain.beans.userfields.*;
import org.letitgo.domain.ports.UserPort;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class RegisterNewUserTest {

	private RegisterNewUser registerNewUser;

	@Mock
	private UserPort userPort;

	@BeforeEach
	public void setUp() {
		registerNewUser = new RegisterNewUser(userPort);
	}

	@Test
	public void shouldReturnTrue() {
		// Arrange
		User user = new User(
			new Username("ahamaide"),
			new BirthDate(LocalDate.of(1990, 1, 1)),
			Identity.HE,
			new Password("password")
		);

		when(this.userPort.register(user)).thenReturn(true);

		// Act
		boolean actualRegister = this.registerNewUser.execute(user);

		// Assert
		boolean expectedRegister = true;

		assertThat(actualRegister).isEqualTo(expectedRegister);
	}

	@Test
	public void shouldReturnFalse() {
		// Arrange
		User user = new User(
			new Username("ahamaide"),
			new BirthDate(LocalDate.of(1990, 1, 1)),
			Identity.HE,
			new Password("password")
		);

		when(this.userPort.register(user)).thenReturn(false);

		// Act
		boolean actualRegister = this.registerNewUser.execute(user);

		// Assert
		boolean expectedRegister = false;

		assertThat(actualRegister).isEqualTo(expectedRegister);
	}

}