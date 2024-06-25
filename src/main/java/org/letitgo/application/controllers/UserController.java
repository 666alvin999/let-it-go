package org.letitgo.application.controllers;

import org.letitgo.application.dtos.in.LoginForm;
import org.letitgo.application.dtos.in.RegisterForm;
import org.letitgo.application.mappers.in.LoginFormMapper;
import org.letitgo.application.mappers.in.RegisterFormMapper;
import org.letitgo.application.presenters.ActionSuccessPresenter;
import org.letitgo.domain.beans.ActionSuccess;
import org.letitgo.domain.beans.User;
import org.letitgo.domain.ports.UserPort;
import org.letitgo.domain.usecases.IsMailFree;
import org.letitgo.domain.usecases.IsUsernameFree;
import org.letitgo.domain.usecases.LogUserIn;
import org.letitgo.domain.usecases.RegisterNewUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@CrossOrigin(origins = "*")
public class UserController {

	private final RegisterNewUser registerNewUser;
	private final RegisterFormMapper registerFormMapper;

	private final LogUserIn logUserIn;
	private final LoginFormMapper loginFormMapper;

	private final IsUsernameFree isUsernameFree;
	private final IsMailFree isMailFree;

	private final ActionSuccessPresenter actionSuccessPresenter;

	@Autowired
	public UserController(UserPort userPort, RegisterFormMapper registerFormMapper, LoginFormMapper loginFormMapper, ActionSuccessPresenter actionSuccessPresenter) {
		this.registerNewUser = new RegisterNewUser(userPort);
		this.logUserIn = new LogUserIn(userPort);
		this.isUsernameFree = new IsUsernameFree(userPort);
		this.isMailFree = new IsMailFree(userPort);
		this.registerFormMapper = registerFormMapper;
		this.loginFormMapper = loginFormMapper;
		this.actionSuccessPresenter = actionSuccessPresenter;
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

	@GetMapping("/isUsernameFree")
	public ResponseEntity<String> isUsernameFree(@RequestParam String username) {
		ActionSuccess actionSuccess = this.isUsernameFree.execute(username);

		return this.actionSuccessPresenter.present(actionSuccess);
	}

	@GetMapping("/isMailFree")
	public ResponseEntity<String> isMailFree(@RequestParam String mail) {
		ActionSuccess actionSuccess = this.isMailFree.execute(mail);

		return this.actionSuccessPresenter.present(actionSuccess);
	}

}
