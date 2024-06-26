package org.letitgo.application.mappers.in;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.letitgo.application.dtos.in.RegisterForm;
import org.letitgo.domain.beans.User;
import org.letitgo.domain.beans.userfields.*;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.letitgo.application.dtos.in.RegisterForm.registerForm;

@ExtendWith(MockitoExtension.class)
class RegisterFormMapperTest {

	private RegisterFormMapper registerFormMapper;

	@BeforeEach
	public void setUp() {
		this.registerFormMapper = new RegisterFormMapper();
	}

	@Test
	public void shouldMapRegisterFormToUser() {
		// Arrange
		RegisterForm registerForm = registerForm()
			.username("ahamaide")
			.mail("mail")
			.birthDate("2024-01-01")
			.identity("HE")
			.password("password")
			.colorTheme("uranus")
			.build();

		// Act
		User actualUser = this.registerFormMapper.mapToUser(registerForm);

		// Assert
		User expectedUser = new User(
			new Username("ahamaide"),
			new Mail("mail"),
			new BirthDate(LocalDate.of(2024, 1, 1)),
			Identity.HE,
			new Password("password"),
			ColorTheme.URANUS,
			new ProfilePicture(null)
		);

		assertThat(actualUser).isEqualTo(expectedUser);
	}

}