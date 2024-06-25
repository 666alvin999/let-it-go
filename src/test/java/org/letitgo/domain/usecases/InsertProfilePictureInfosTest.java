package org.letitgo.domain.usecases;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.letitgo.domain.beans.ActionSuccess;
import org.letitgo.domain.beans.ProfilePictureInfos;
import org.letitgo.domain.beans.fileinfosfields.File;
import org.letitgo.domain.beans.profilepicturesinfosfields.Extension;
import org.letitgo.domain.beans.userfields.Username;
import org.letitgo.domain.ports.UserPort;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.FileInputStream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class InsertProfilePictureInfosTest {

	private InsertProfilePictureInfos insertProfilePictureInfos;

	@Mock
	private UserPort userPort;

	@BeforeEach
	public void setUp() {
		this.insertProfilePictureInfos = new InsertProfilePictureInfos(userPort);
	}

	@Test
	@SneakyThrows
	public void shouldInsertProfilePicture() {
	    // Arrange
	    ProfilePictureInfos profilePictureInfos = new ProfilePictureInfos(
			new File(new FileInputStream("src/test/resources/test_img.png")),
		    new Username("ahamaide"),
		    new Extension("png")
	    );

		when(this.userPort.insertProfilePictureInfosByUsername(profilePictureInfos)).thenReturn(new ActionSuccess(true));

	    // Act
		ActionSuccess actualActionSuccess = this.insertProfilePictureInfos.execute(profilePictureInfos);

	    // Assert
	    ActionSuccess expectedActionSuccess = new ActionSuccess(true);

		assertThat(actualActionSuccess).isEqualTo(expectedActionSuccess);
	}

}