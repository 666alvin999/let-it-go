package org.letitgo.domain.usecases;

import org.letitgo.domain.beans.ActionSuccess;
import org.letitgo.domain.ports.UserPort;

public class IsMailFree {

	private final UserPort userPort;

	public IsMailFree(UserPort userPort) {
		this.userPort = userPort;
	}

	public ActionSuccess execute(String mail) {
		return this.userPort.isMailFree(mail);
	}

}
