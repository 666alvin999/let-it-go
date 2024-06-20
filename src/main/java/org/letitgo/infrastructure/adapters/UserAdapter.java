package org.letitgo.infrastructure.adapters;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.letitgo.domain.beans.ActionSuccess;
import org.letitgo.domain.beans.User;
import org.letitgo.domain.ports.UserPort;
import org.letitgo.infrastructure.daos.UserDao;
import org.letitgo.infrastructure.dtos.UserDTO;
import org.letitgo.infrastructure.mappers.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserAdapter implements UserPort {

	private UserDao userDao;
	private UserMapper userMapper;

	@Autowired
	public UserAdapter(UserDao userDao, UserMapper userMapper) {
		this.userDao = userDao;
		this.userMapper = userMapper;
	}

	public ActionSuccess register(User user) {
		UserDTO userDTO = this.userMapper.mapToDTO(user);

		return this.userDao.register(userDTO);
	}

}
