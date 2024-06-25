package org.letitgo.domain.usecases;

import org.letitgo.domain.beans.ActionSuccess;
import org.letitgo.domain.beans.ProfilePictureInfos;
import org.letitgo.domain.ports.UserPort;

public class UploadProfilePictureFile {

	private UserPort userPort;

	public UploadProfilePictureFile(UserPort userPort) {
		this.userPort = userPort;
	}

	public ActionSuccess execute(ProfilePictureInfos profilePictureInfos) {
		return this.userPort.uploadProfilePictureFile(profilePictureInfos);
	}

}
