package org.letitgo.infrastructure.mappers;

import org.letitgo.domain.beans.User;
import org.letitgo.domain.beans.userfields.*;
import org.letitgo.infrastructure.dtos.UserDTO;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static java.util.Objects.nonNull;
import static org.letitgo.infrastructure.dtos.UserDTO.userDTO;

@Component
public class UserMapper {

	private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	public UserDTO mapToDTO(User user) {
		String identity;

		if (user.identity() == Identity.HE) {
			identity = "HE";
		} else if (user.identity() == Identity.SHE) {
			identity = "SHE";
		} else {
			identity = "THEY";
		}

		String birthDate = nonNull(user.birthDate().value()) ? this.dateFormatter.format(user.birthDate().value()) : null;

		return userDTO()
			.username(user.username().value())
			.mail(user.mail().value())
			.birthDate(birthDate)
			.userIdentity(identity)
			.pwd(user.password().value())
			.colorTheme(user.colorTheme().getValue())
			.profilePicture(user.profilePicture().value())
			.build();
	}

	public User mapToDomain(UserDTO userDTO) {
		Identity identity;

		if ("HE".equals(userDTO.getUserIdentity())) {
			identity = Identity.HE;
		} else if ("SHE".equals(userDTO.getUserIdentity())) {
			identity = Identity.SHE;
		} else {
			identity = Identity.THEY;
		}

		return new User(
			new Username(userDTO.getUsername()),
			new Mail(userDTO.getMail()),
			new BirthDate(LocalDate.parse(userDTO.getBirthDate(), this.dateFormatter)),
			identity,
			new Password(userDTO.getPwd()),
			ColorTheme.valueOf(nonNull(userDTO.getColorTheme()) ? userDTO.getColorTheme().toUpperCase() : "NULL"),
			new ProfilePicture(userDTO.getProfilePicture())
		);
	}
}
