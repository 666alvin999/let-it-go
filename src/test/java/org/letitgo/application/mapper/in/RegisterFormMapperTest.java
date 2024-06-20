package org.letitgo.application.mapper.in;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.letitgo.application.dtos.in.RegisterForm;
import org.letitgo.domain.beans.User;
import org.letitgo.domain.beans.userfields.BirthDate;
import org.letitgo.domain.beans.userfields.Identity;
import org.letitgo.domain.beans.userfields.Password;
import org.letitgo.domain.beans.userfields.Username;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.letitgo.application.dtos.in.RegisterForm.registerForm;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.ReflectionTestUtils.setField;

@ExtendWith(MockitoExtension.class)
class RegisterFormMapperTest {

	private RegisterFormMapper registerFormMapper;

	@Mock
	PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

	@BeforeEach
	public void setUp() {
		this.registerFormMapper = new RegisterFormMapper();
		setField(registerFormMapper, "passwordEncoder", this.passwordEncoder);
	}

	@Test
	public void shouldMapRegisterFormToUser() {
		// Arrange
		RegisterForm registerForm = registerForm()
			.username("ahamaide")
			.birthDate("2024-01-01")
			.identity("HE")
			.password("password")
			.build();

		when(this.passwordEncoder.encode("password")).thenReturn("encoded");

		// Act
		User actualUser = this.registerFormMapper.mapToUser(registerForm);

		// Assert
		User expectedUser = new User(
			new Username("ahamaide"),
			new BirthDate(LocalDate.of(2024, 1, 1)),
			Identity.HE,
			new Password("encoded")
		);

		assertThat(actualUser).isEqualTo(expectedUser);
	}

}