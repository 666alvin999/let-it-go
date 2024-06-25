package org.letitgo.application.mappers.in;

import org.letitgo.application.dtos.in.RegisterForm;
import org.letitgo.domain.beans.User;
import org.letitgo.domain.beans.userfields.*;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static java.util.Objects.nonNull;

@Component
public class RegisterFormMapper {

	private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	public User mapToUser(RegisterForm registerForm) {
		Identity identity;

		if ("HE".equals(registerForm.getIdentity())) {
			identity = Identity.HE;
		} else if ("SHE".equals(registerForm.getIdentity())) {
			identity = Identity.SHE;
		} else {
			identity = Identity.THEY;
		}

		return new User(
			new Username(registerForm.getUsername()),
			new Mail(registerForm.getMail()),
			new BirthDate(LocalDate.parse(registerForm.getBirthDate(), this.dateFormatter)),
			identity,
			new Password(registerForm.getPassword()),
			ColorTheme.valueOf(nonNull(registerForm.getColorTheme()) ? registerForm.getColorTheme().toUpperCase() : "NULL"),
			new ProfilePicture(null)
		);
	}

}
