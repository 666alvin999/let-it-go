package org.letitgo.domain.usecases;

import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.letitgo.domain.beans.ActionSuccess;
import org.letitgo.domain.ports.UserPort;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class DeleteProfilePictureInfosTest {

	private DeleteProfilePictureInfos deleteProfilePictureInfos;

	@Mock
	private UserPort userPort;

	@BeforeEach
	public void setUp() {
		this.deleteProfilePictureInfos = new DeleteProfilePictureInfos(userPort);
	}

	@Test
	@SneakyThrows
	public void shouldInsertProfilePicture() {
		// Arrange
		String username = "ahamaide";

		when(this.userPort.deleteProfilePictureInfosByUsername(username)).thenReturn(new ActionSuccess(true));

		// Act
		ActionSuccess actualActionSuccess = this.deleteProfilePictureInfos.execute(username);

		// Assert
		ActionSuccess expectedActionSuccess = new ActionSuccess(true);

		assertThat(actualActionSuccess).isEqualTo(expectedActionSuccess);
	}

}