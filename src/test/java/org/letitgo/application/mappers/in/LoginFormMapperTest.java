package org.letitgo.application.mappers.in;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.letitgo.application.dtos.in.LoginForm;
import org.letitgo.domain.beans.User;
import org.letitgo.domain.beans.userfields.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.letitgo.application.dtos.in.LoginForm.loginForm;

class LoginFormMapperTest {

	private LoginFormMapper loginFormMapper;

	@BeforeEach
	public void setUp() {
		this.loginFormMapper = new LoginFormMapper();
	}

	@Test
	public void shouldMapRegisterFormToUser() {
		// Arrange
		LoginForm loginForm = loginForm()
			.username("ahamaide")
			.mail(null)
			.password("password")
			.build();

		// Act
		User actualUser = this.loginFormMapper.mapToUser(loginForm);

		// Assert
		User expectedUser = new User(
			new Username("ahamaide"),
			new Mail(null),
			new BirthDate(null),
			null,
			new Password("password"),
			ColorTheme.NULL,
			new ProfilePicture(null)
		);

		assertThat(actualUser).isEqualTo(expectedUser);
	}

}