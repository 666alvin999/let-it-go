package org.letitgo.domain.usecases;

import org.letitgo.domain.beans.User;
import org.letitgo.domain.ports.UserPort;

public class RegisterNewUser {

	private final UserPort userPort;

	public RegisterNewUser(UserPort userPort) {
		this.userPort = userPort;
	}

	public boolean execute(User user) {
		return this.userPort.register(user);
	}

}
