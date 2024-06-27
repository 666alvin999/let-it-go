package org.letitgo.application.mappers.in;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.letitgo.application.dtos.in.ProfilePictureForm;
import org.letitgo.domain.beans.ProfilePictureInfos;
import org.letitgo.domain.beans.fileinfosfields.File;
import org.letitgo.domain.beans.profilepicturesinfosfields.Extension;
import org.letitgo.domain.beans.userfields.Username;
import org.springframework.mock.web.MockMultipartFile;

import java.io.FileInputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;
import static org.letitgo.application.dtos.in.ProfilePictureForm.profilePictureForm;

class ProfilePictureFormMapperTest {

	private ProfilePictureFormMapper mapper;

	@BeforeEach
	public void setUp() {
		this.mapper = new ProfilePictureFormMapper();
	}

	@Test
	@SneakyThrows
	public void shouldMapToProfilePictureInfos() {
		// Arrange
		ProfilePictureForm profilePictureForm = profilePictureForm()
			.multipartFile(new MockMultipartFile("test_img", new FileInputStream("src/test/resources/test_img.png")))
			.username("ahamaide")
			.extension("png")
			.build();

		// Act
		ProfilePictureInfos actualProfilePictureInfos = mapper.mapToProfilePictureInfos(profilePictureForm);

		// Assert
		ProfilePictureInfos expectedProfilePictureInfos = new ProfilePictureInfos(
			new File(new FileInputStream("src/test/resources/test_img.png")),
			new Username("ahamaide"),
			new Extension("png")
		);

		assertAll(
			() -> assertThat(actualProfilePictureInfos.file().value().readAllBytes()).isEqualTo(expectedProfilePictureInfos.file().value().readAllBytes()),
			() -> assertThat(actualProfilePictureInfos.username().value()).isEqualTo(expectedProfilePictureInfos.username().value()),
			() -> assertThat(actualProfilePictureInfos.extension().value()).isEqualTo(expectedProfilePictureInfos.extension().value())
		);
	}


}