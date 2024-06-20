package org.letitgo.application.dtos.in;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class LoginForm {

	private String username;
	private String mail;
	private String password;

	public static LoginFormBuilder loginForm() {
		return new LoginFormBuilder();
	}

}
