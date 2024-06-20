package org.letitgo.domain.usecases;

import org.letitgo.domain.beans.ActionSuccess;
import org.letitgo.domain.beans.User;
import org.letitgo.domain.ports.UserPort;

public class LogUserIn {

	private final UserPort userPort;

	public LogUserIn(UserPort userPort) {
		this.userPort = userPort;
	}

	public ActionSuccess execute(User user) {
		return this.userPort.logUserIn(user);
	}

}
