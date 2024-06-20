package org.letitgo.application.mappers.in;

import org.letitgo.application.dtos.in.LoginForm;
import org.letitgo.domain.beans.User;
import org.letitgo.domain.beans.userfields.BirthDate;
import org.letitgo.domain.beans.userfields.Mail;
import org.letitgo.domain.beans.userfields.Password;
import org.letitgo.domain.beans.userfields.Username;
import org.springframework.stereotype.Component;

@Component
public class LoginFormMapper {

	public User mapToUser(LoginForm loginForm) {
		return new User(
			new Username(loginForm.getUsername()),
			new Mail(loginForm.getMail()),
			new BirthDate(null),
			null,
			new Password(loginForm.getPassword())
		);
	}

}
