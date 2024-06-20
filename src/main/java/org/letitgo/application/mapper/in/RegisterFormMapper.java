package org.letitgo.application.mapper.in;

import org.letitgo.application.dtos.in.RegisterForm;
import org.letitgo.domain.beans.User;
import org.letitgo.domain.beans.userfields.BirthDate;
import org.letitgo.domain.beans.userfields.Identity;
import org.letitgo.domain.beans.userfields.Password;
import org.letitgo.domain.beans.userfields.Username;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class RegisterFormMapper {

	private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
	private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

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
			new BirthDate(LocalDate.parse(registerForm.getBirthDate(), this.dateFormatter)),
			identity,
			new Password(this.passwordEncoder.encode(registerForm.getPassword()))
		);
	}

}