package org.letitgo.infrastructure.mappers;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.letitgo.domain.beans.ProfilePictureInfos;
import org.letitgo.domain.beans.fileinfosfields.File;
import org.letitgo.domain.beans.profilepicturesinfosfields.Extension;
import org.letitgo.domain.beans.userfields.Username;
import org.letitgo.infrastructure.dtos.ProfilePictureInfosDTO;

import java.io.FileInputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.letitgo.infrastructure.dtos.ProfilePictureInfosDTO.profilePictureInfosDTO;

class ProfilePictureInfosMapperTest {

	private ProfilePictureInfosMapper mapper;

	@BeforeEach
	public void setUp() {
		this.mapper = new ProfilePictureInfosMapper();
	}

	@Test
	@SneakyThrows
	public void shouldMapToProfilePictureInfosDTO() {
		// Arrange
		ProfilePictureInfos profilePictureInfos = new ProfilePictureInfos(
			new File(new FileInputStream("src/test/resources/test_img.png")),
			new Username("ahamaide"),
			new Extension("png")
		);

		// Act
		ProfilePictureInfosDTO actualProfilePictureInfosDTO = this.mapper.mapToDTO(profilePictureInfos);

		// Assert
		ProfilePictureInfosDTO expectedProfilePictureInfosDTO = profilePictureInfosDTO()
			.file(new FileInputStream("src/test/resources/test_img.png"))
			.fileName("/ahamaide/ahamaide.png")
			.build();

		assertAll(
			() -> assertThat(actualProfilePictureInfosDTO.getFile().readAllBytes()).isEqualTo(expectedProfilePictureInfosDTO.getFile().readAllBytes()),
			() -> assertThat(actualProfilePictureInfosDTO.getFileName()).isEqualTo(expectedProfilePictureInfosDTO.getFileName())
		);
	}

}