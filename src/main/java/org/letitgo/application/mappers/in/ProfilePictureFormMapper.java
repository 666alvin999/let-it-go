package org.letitgo.application.mappers.in;

import org.letitgo.application.dtos.in.ProfilePictureForm;
import org.letitgo.domain.beans.ProfilePictureInfos;
import org.letitgo.domain.beans.fileinfosfields.File;
import org.letitgo.domain.beans.profilepicturesinfosfields.Extension;
import org.letitgo.domain.beans.userfields.Username;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;

import static java.util.Objects.isNull;

@Component
public class ProfilePictureFormMapper {

	private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	public ProfilePictureInfos mapToProfilePictureInfos(ProfilePictureForm profilePictureForm) {
		try {
			File file;
			if (isNull(profilePictureForm.getMultipartFile())) {
				file = new File(null);
			} else {
				file = new File(profilePictureForm.getMultipartFile().getInputStream());
			}

			return new ProfilePictureInfos(
				file,
				new Username(profilePictureForm.getUsername()),
				new Extension(profilePictureForm.getExtension())
			);
		} catch (Exception e) {
			return new ProfilePictureInfos(
				new File(null),
				new Username(null),
				new Extension(null)
			);
		}
	}

}
