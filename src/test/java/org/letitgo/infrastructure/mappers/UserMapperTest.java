package org.letitgo.infrastructure.mappers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.letitgo.domain.beans.User;
import org.letitgo.domain.beans.userfields.*;
import org.letitgo.infrastructure.dtos.UserDTO;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.letitgo.infrastructure.dtos.UserDTO.userDTO;

class UserMapperTest {

	private UserMapper userMapper;

	@BeforeEach
	public void setUp() {
		this.userMapper = new UserMapper();
	}

	@Test
	public void shouldMapUserToUserDTO() {
		// Arrange
		User user = new User(
			new Username("ahamaide"),
			new Mail("mail"),
			new BirthDate(LocalDate.of(2024, 1, 1)),
			Identity.HE,
			new Password("password"),
			ColorTheme.URANUS,
			new ProfilePicture(null)
		);

		// Act
		UserDTO actualUserDTO = this.userMapper.mapToDTO(user);

		// Assert
		UserDTO expectedUserDTO = userDTO()
			.username("ahamaide")
			.mail("mail")
			.birthDate("2024-01-01")
			.userIdentity("HE")
			.pwd("password")
			.colorTheme("uranus")
			.build();

		assertThat(actualUserDTO).isEqualTo(expectedUserDTO);
	}

	@Test
	public void shouldMapUserDTOToUser() {
		// Arrange
		UserDTO userDTO = userDTO()
			.username("ahamaide")
			.mail("mail")
			.birthDate("2024-01-01")
			.userIdentity("HE")
			.pwd("password")
			.colorTheme("uranus")
			.build();

		// Act
		User actualUser = this.userMapper.mapToDomain(userDTO);

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