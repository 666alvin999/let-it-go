package org.letitgo.infrastructure.dtos;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class UserDTO {

	private String username;
	private String mail;
	private String birthDate;
	private String userIdentity;
	private String pwd;
	private String colorTheme;
	private String profilePicture;

	public static UserDTOBuilder userDTO() {
		return UserDTO.builder();
	}

}
