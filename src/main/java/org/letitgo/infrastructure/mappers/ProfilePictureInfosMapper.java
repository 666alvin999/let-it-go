package org.letitgo.infrastructure.mappers;

import org.letitgo.domain.beans.ProfilePictureInfos;
import org.letitgo.infrastructure.dtos.ProfilePictureInfosDTO;
import org.springframework.stereotype.Component;

@Component
public class ProfilePictureInfosMapper {

	public ProfilePictureInfosDTO mapToDTO(ProfilePictureInfos profilePictureInfos) {
		return new ProfilePictureInfosDTO(
			profilePictureInfos.file().value(),
			"/" + profilePictureInfos.username().value() + "/" + profilePictureInfos.username().value() + "." + profilePictureInfos.extension().value()
		);
	}

}
