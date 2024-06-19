package org.letitgo.infrastructure.mappers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.letitgo.domain.beans.User;
import org.letitgo.domain.beans.userfields.BirthDate;
import org.letitgo.domain.beans.userfields.Identity;
import org.letitgo.domain.beans.userfields.Password;
import org.letitgo.domain.beans.userfields.Username;
import org.letitgo.infrastructure.dtos.UserDTO;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
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
			new BirthDate(LocalDate.of(2024, 1, 1)),
			Identity.HE,
			new Password("password")
		);

	    // Act
		UserDTO actualUserDTO = this.userMapper.mapToDTO(user);

	    // Assert
	    UserDTO expectedUserDTO = userDTO()
		    .username("ahamaide")
		    .birthDate("2024-01-01")
		    .userIdentity("HE")
		    .pwd("password")
		    .build();

		assertThat(actualUserDTO).isEqualTo(expectedUserDTO);
	}

	@Test
	public void shouldMapUserDTOToUser() {
		// Arrange
		UserDTO userDTO = userDTO()
			.username("ahamaide")
			.birthDate("2024-01-01")
			.userIdentity("HE")
			.pwd("password")
			.build();

		// Act
		User actualUser = this.userMapper.mapToDomain(userDTO);

		// Assert
		User expectedUser = new User(
			new Username("ahamaide"),
			new BirthDate(LocalDate.of(2024, 1, 1)),
			Identity.HE,
			new Password("password")
		);

		assertThat(actualUser).isEqualTo(expectedUser);
	}

}