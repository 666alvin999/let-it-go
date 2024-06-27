package org.letitgo.domain.usecases;

import org.letitgo.domain.beans.ActionSuccess;
import org.letitgo.domain.beans.ProfilePictureInfos;
import org.letitgo.domain.ports.UserPort;

public class InsertProfilePictureInfos {

	private final UserPort userPort;

	public InsertProfilePictureInfos(UserPort userPort) {
		this.userPort = userPort;
	}

	public ActionSuccess execute(ProfilePictureInfos profilePictureInfos) {
		return this.userPort.insertProfilePictureInfosByUsername(profilePictureInfos);
	}

}
