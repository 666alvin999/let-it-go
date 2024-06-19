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
	private String birthDate;
	private String identity;
	private String password;

	public static UserDTOBuilder userDTO() {
		return UserDTO.builder();
	}

}
