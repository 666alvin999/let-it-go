package org.letitgo.domain.usecases;

import org.letitgo.domain.beans.ActionSuccess;
import org.letitgo.domain.ports.UserPort;

public class DeleteProfilePictureInfos {

	private UserPort userPort;

	public DeleteProfilePictureInfos(UserPort userPort) {
		this.userPort = userPort;
	}

	public ActionSuccess execute(String username) {
		return this.userPort.deleteProfilePictureInfosByUsername(username);
	}

}
