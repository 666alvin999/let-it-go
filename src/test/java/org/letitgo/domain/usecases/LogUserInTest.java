package org.letitgo.domain.usecases;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.letitgo.domain.beans.ActionSuccess;
import org.letitgo.domain.beans.User;
import org.letitgo.domain.beans.userfields.*;
import org.letitgo.domain.ports.UserPort;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LogUserInTest {

	private LogUserIn logUserIn;

	@Mock
	private UserPort userPort;

	@BeforeEach
	public void setUp() {
		this.logUserIn = new LogUserIn(userPort);
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

		when(this.userPort.logUserIn(user)).thenReturn(new ActionSuccess(true));

	    // Act
		ActionSuccess actualActionSuccess = this.logUserIn.execute(user);

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

		when(this.userPort.logUserIn(user)).thenReturn(new ActionSuccess(true));

		// Act
		ActionSuccess actualActionSuccess = this.logUserIn.execute(user);

		// Assert
		ActionSuccess expectedActionSuccess = new ActionSuccess(true);

		assertThat(actualActionSuccess).isEqualTo(expectedActionSuccess);
	}

	@Test
	public void shouldLogUserInFailure() {
		// ArrangeL
		User user = new User(
			new Username("ahamaide"),
			new Mail(null),
			new BirthDate(null),
			null,
			new Password("password")
		);

		when(this.userPort.logUserIn(user)).thenReturn(new ActionSuccess(false, Optional.of("Le mot de passe ou l'identifiant est erroné.")));

		// Act
		ActionSuccess actualActionSuccess = this.logUserIn.execute(user);

		// Assert
		ActionSuccess expectedActionSuccess = new ActionSuccess(false, Optional.of("Le mot de passe ou l'identifiant est erroné."));

		assertThat(actualActionSuccess).isEqualTo(expectedActionSuccess);
	}

}