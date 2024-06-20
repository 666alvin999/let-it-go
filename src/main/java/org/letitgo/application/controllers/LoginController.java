package org.letitgo.application.controllers;

import com.dropbox.core.DbxException;
import org.letitgo.application.dtos.in.LoginForm;
import org.letitgo.application.dtos.in.RegisterForm;
import org.letitgo.application.mappers.in.LoginFormMapper;
import org.letitgo.application.mappers.in.RegisterFormMapper;
import org.letitgo.application.presenters.ActionSuccessPresenter;
import org.letitgo.domain.beans.ActionSuccess;
import org.letitgo.domain.beans.User;
import org.letitgo.domain.ports.UserPort;
import org.letitgo.domain.usecases.LogUserIn;
import org.letitgo.domain.usecases.RegisterNewUser;
import org.letitgo.infrastructure.daos.DropboxDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

@Controller
public class LoginController {

	private final RegisterNewUser registerNewUser;
	private final RegisterFormMapper registerFormMapper;

	private final LogUserIn logUserIn;
	private final LoginFormMapper loginFormMapper;

	private final ActionSuccessPresenter actionSuccessPresenter;

	private final DropboxDao dropboxDao;

	@Autowired
	public LoginController(UserPort userPort, RegisterFormMapper registerFormMapper, LoginFormMapper loginFormMapper, ActionSuccessPresenter actionSuccessPresenter, DropboxDao dropboxDao) throws DbxException, IOException {
		this.registerNewUser = new RegisterNewUser(userPort);
		this.logUserIn = new LogUserIn(userPort);
		this.registerFormMapper = registerFormMapper;
		this.loginFormMapper = loginFormMapper;
		this.actionSuccessPresenter = actionSuccessPresenter;
		this.dropboxDao = dropboxDao;
	}

	@PostMapping("/register")
	public ResponseEntity<String> register(@RequestBody RegisterForm registerForm) {
		User user = this.registerFormMapper.mapToUser(registerForm);
		ActionSuccess actionSuccess = this.registerNewUser.execute(user);

		return this.actionSuccessPresenter.present(actionSuccess);
	}

	@PostMapping("/login")
	public ResponseEntity<String> login(@RequestBody LoginForm loginForm) {
		User user = this.loginFormMapper.mapToUser(loginForm);
		ActionSuccess actionSuccess = this.logUserIn.execute(user);

		return this.actionSuccessPresenter.present(actionSuccess);
	}

	@PostMapping("/uploadFile")
	public ResponseEntity<String> uploadFile(@RequestBody MultipartFile file) {
		return ResponseEntity.ok("");
	}

}
