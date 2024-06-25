package org.letitgo.domain.usecases;

import org.letitgo.domain.beans.ActionSuccess;
import org.letitgo.domain.beans.ProfilePictureInfos;
import org.letitgo.domain.ports.UserPort;

public class DeleteProfilePictureFile {

	private UserPort userPort;

	public DeleteProfilePictureFile(UserPort userPort) {
		this.userPort = userPort;
	}

	public ActionSuccess execute(ProfilePictureInfos profilePictureInfos) {
		return this.userPort.deleteProfilePictureFile(profilePictureInfos);
	}

}
