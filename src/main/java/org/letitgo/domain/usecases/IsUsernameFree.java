package org.letitgo.domain.usecases;

import org.letitgo.domain.beans.ActionSuccess;
import org.letitgo.domain.ports.UserPort;

public class IsUsernameFree {

	private final UserPort userPort;

	public IsUsernameFree(UserPort userPort) {
		this.userPort = userPort;
	}

	public ActionSuccess execute(String username) {
		return this.userPort.isUsernameFree(username);
	}

}
