package org.letitgo.application.dtos.in;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class ProfilePictureForm {

	private MultipartFile multipartFile;
	private String username;
	private String extension;

	public static ProfilePictureFormBuilder profilePictureForm() {
		return new ProfilePictureFormBuilder();
	}

}
