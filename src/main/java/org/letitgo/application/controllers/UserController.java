package org.letitgo.application.controllers;

import org.letitgo.application.dtos.in.LoginForm;
import org.letitgo.application.dtos.in.ProfilePictureForm;
import org.letitgo.application.dtos.in.RegisterForm;
import org.letitgo.application.mappers.in.LoginFormMapper;
import org.letitgo.application.mappers.in.ProfilePictureFormMapper;
import org.letitgo.application.mappers.in.RegisterFormMapper;
import org.letitgo.application.presenters.ActionSuccessPresenter;
import org.letitgo.domain.beans.ActionSuccess;
import org.letitgo.domain.beans.ProfilePictureInfos;
import org.letitgo.domain.beans.User;
import org.letitgo.domain.ports.UserPort;
import org.letitgo.domain.usecases.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@CrossOrigin(origins = "*")
public class UserController {

	private final RegisterNewUser registerNewUser;
	private final RegisterFormMapper registerFormMapper;
	private final UploadProfilePictureFile uploadProfilePictureFile;
	private final DeleteProfilePictureFile deleteProfilePictureFile;
	private final InsertProfilePictureInfos insertProfilePictureInfos;
	private final DeleteProfilePictureInfos deleteProfilePictureInfos;
	private final LogUserIn logUserIn;
	private final LoginFormMapper loginFormMapper;
	private final ProfilePictureFormMapper profilePictureFormMapper;

	private final IsUsernameFree isUsernameFree;
	private final IsMailFree isMailFree;

	private final ActionSuccessPresenter actionSuccessPresenter;

	@Autowired
	public UserController(UserPort userPort, RegisterFormMapper registerFormMapper, LoginFormMapper loginFormMapper, ProfilePictureFormMapper profilePictureFormMapper, ActionSuccessPresenter actionSuccessPresenter) {
		this.registerNewUser = new RegisterNewUser(userPort);
		this.logUserIn = new LogUserIn(userPort);
		this.insertProfilePictureInfos = new InsertProfilePictureInfos(userPort);
		this.deleteProfilePictureFile = new DeleteProfilePictureFile(userPort);
		this.deleteProfilePictureInfos = new DeleteProfilePictureInfos(userPort);
		this.uploadProfilePictureFile = new UploadProfilePictureFile(userPort);
		this.isUsernameFree = new IsUsernameFree(userPort);
		this.isMailFree = new IsMailFree(userPort);
		this.registerFormMapper = registerFormMapper;
		this.loginFormMapper = loginFormMapper;
		this.profilePictureFormMapper = profilePictureFormMapper;
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

	@PostMapping(value = "/uploadProfilePicture", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<String> uploadProfilePicture(@ModelAttribute ProfilePictureForm profilePictureForm) {
		ProfilePictureInfos profilePictureInfos = this.profilePictureFormMapper.mapToProfilePictureInfos(profilePictureForm);

		ActionSuccess actionSuccess = this.insertProfilePictureInfos.execute(profilePictureInfos);

		if (!actionSuccess.success()) {
			return this.actionSuccessPresenter.present(actionSuccess);
		}

		ActionSuccess uploadFileSuccess = this.uploadProfilePictureFile.execute(profilePictureInfos);

		if (!uploadFileSuccess.success()) {
			this.deleteProfilePictureInfos.execute(profilePictureInfos.username().value());

			return this.actionSuccessPresenter.present(new ActionSuccess(false, Optional.of("Nous n'avons pas réussi à importer votre photo. Veuillez réessayer plus tard.")));
		}

		return this.actionSuccessPresenter.present(uploadFileSuccess);
	}

	@PostMapping(value = "/deleteProfilePicture", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
	public ResponseEntity<String> deleteProfilePicture(@ModelAttribute ProfilePictureForm profilePictureForm) {
		ProfilePictureInfos profilePictureInfos = this.profilePictureFormMapper.mapToProfilePictureInfos(profilePictureForm);

		ActionSuccess actionSuccess = this.deleteProfilePictureInfos.execute(profilePictureInfos.username().value());

		if (!actionSuccess.success()) {
			return this.actionSuccessPresenter.present(actionSuccess);
		}

		ActionSuccess deleteFileSuccess = this.deleteProfilePictureFile.execute(profilePictureInfos);

		if (!deleteFileSuccess.success()) {
			this.insertProfilePictureInfos.execute(profilePictureInfos);

			return this.actionSuccessPresenter.present(new ActionSuccess(false, Optional.of("Nous n'avons pas réussi à supprimer votre photo. Veuillez réessayer plus tard.")));
		}

		return this.actionSuccessPresenter.present(deleteFileSuccess);
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
