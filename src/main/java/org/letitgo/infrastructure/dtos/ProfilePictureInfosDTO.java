package org.letitgo.infrastructure.dtos;

import lombok.*;

import java.io.InputStream;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class ProfilePictureInfosDTO {

	private InputStream file;
	private String fileName;

	public static ProfilePictureInfosDTOBuilder profilePictureInfosDTO() {
		return ProfilePictureInfosDTO.builder();
	}

}
