package org.letitgo.application.controllers;

import oracle.jdbc.proxy.annotation.Post;
import org.letitgo.application.presenters.ActionSuccessPresenter;
import org.letitgo.domain.ports.UserPort;
import org.letitgo.domain.usecases.RegisterNewUser;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class LoginController {

	private final RegisterNewUser registerNewUser;
	private final ActionSuccessPresenter actionSuccessPresenter;

	public LoginController(UserPort userPort, ActionSuccessPresenter actionSuccessPresenter) {
		this.registerNewUser = new RegisterNewUser(userPort);
		this.actionSuccessPresenter = actionSuccessPresenter;
	}

	@PostMapping("/register")
	public ResponseEntity<String> register() {
		return ResponseEntity.ok("");
	}

}
