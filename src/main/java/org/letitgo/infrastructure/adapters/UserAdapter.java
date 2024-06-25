package org.letitgo.infrastructure.adapters;

import org.letitgo.domain.beans.ActionSuccess;
import org.letitgo.domain.beans.ProfilePictureInfos;
import org.letitgo.domain.beans.User;
import org.letitgo.domain.ports.UserPort;
import org.letitgo.infrastructure.daos.DropboxDao;
import org.letitgo.infrastructure.daos.UserDao;
import org.letitgo.infrastructure.dtos.ProfilePictureInfosDTO;
import org.letitgo.infrastructure.dtos.UserDTO;
import org.letitgo.infrastructure.mappers.ProfilePictureInfosMapper;
import org.letitgo.infrastructure.mappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserAdapter implements UserPort {

	private final UserDao userDao;
	private final DropboxDao dropboxDao;
	private final UserMapper userMapper;
	private final ProfilePictureInfosMapper profilePictureInfosMapper;

	@Autowired
	public UserAdapter(UserDao userDao, DropboxDao dropboxDao, UserMapper userMapper, ProfilePictureInfosMapper profilePictureInfosMapper) {
		this.userDao = userDao;
		this.dropboxDao = dropboxDao;
		this.userMapper = userMapper;
		this.profilePictureInfosMapper = profilePictureInfosMapper;
	}

	public ActionSuccess register(User user) {
		UserDTO userDTO = this.userMapper.mapToDTO(user);

		return this.userDao.register(userDTO);
	}

	public ActionSuccess insertProfilePictureInfosByUsername(ProfilePictureInfos profilePictureInfos) {
		ProfilePictureInfosDTO profilePictureInfosDTO = this.profilePictureInfosMapper.mapToDTO(profilePictureInfos);

		return this.userDao.insertProfilePictureByUsername(profilePictureInfosDTO.getFileName(), profilePictureInfos.username().value());
	}

	public ActionSuccess deleteProfilePictureInfosByUsername(String username) {
		return this.userDao.deleteProfilePictureByUsername(username);
	}

	public ActionSuccess uploadProfilePictureFile(ProfilePictureInfos profilePictureInfos) {
		ProfilePictureInfosDTO profilePictureInfosDTO = this.profilePictureInfosMapper.mapToDTO(profilePictureInfos);

		return this.dropboxDao.uploadFile(profilePictureInfosDTO);
	}

	public ActionSuccess deleteProfilePictureFile(ProfilePictureInfos profilePictureInfos) {
		ProfilePictureInfosDTO profilePictureInfosDTO = this.profilePictureInfosMapper.mapToDTO(profilePictureInfos);

		return this.dropboxDao.deleteFile(profilePictureInfosDTO.getFileName());
	}

	public ActionSuccess logUserIn(User user) {
		UserDTO userDTO = this.userMapper.mapToDTO(user);

		return this.userDao.logUserIn(userDTO);
	}

	public ActionSuccess isUsernameFree(String username) {
		return this.userDao.isUsernameFree(username);
	}

	public ActionSuccess isMailFree(String mail) {
		return this.userDao.isMailFree(mail);
	}

}
