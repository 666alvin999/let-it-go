package org.letitgo.application.dtos.in;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class RegisterForm {

	private String username;
	private String mail;
	private String birthDate;
	private String identity;
	private String password;
	private String colorTheme;

	public static RegisterFormBuilder registerForm() {
		return RegisterForm.builder();
	}

}
