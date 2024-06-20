package org.letitgo.application.mappers.in;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.letitgo.application.dtos.in.LoginForm;
import org.letitgo.application.dtos.in.RegisterForm;
import org.letitgo.domain.beans.User;
import org.letitgo.domain.beans.userfields.*;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.letitgo.application.dtos.in.LoginForm.loginForm;
import static org.letitgo.application.dtos.in.RegisterForm.registerForm;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;

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
			new Password("password")
		);

		assertThat(actualUser).isEqualTo(expectedUser);
	}

}