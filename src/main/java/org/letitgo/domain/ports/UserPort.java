package org.letitgo.domain.ports;

import org.letitgo.domain.beans.ActionSuccess;
import org.letitgo.domain.beans.ProfilePictureInfos;
import org.letitgo.domain.beans.User;

public interface UserPort {

	ActionSuccess register(User user);

	ActionSuccess uploadProfilePictureFile(ProfilePictureInfos profilePictureInfos);

	ActionSuccess insertProfilePictureInfosByUsername(ProfilePictureInfos profilePictureInfos);

	ActionSuccess deleteProfilePictureInfosByUsername(String username);

	ActionSuccess deleteProfilePictureFile(ProfilePictureInfos profilePictureInfos);

	ActionSuccess logUserIn(User user);

	ActionSuccess isUsernameFree(String username);

	ActionSuccess isMailFree(String mail);

}
