package org.letitgo.domain.usecases;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.letitgo.domain.beans.ActionSuccess;
import org.letitgo.domain.ports.UserPort;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class IsUsernameFreeTest {

	private IsUsernameFree isUsernameFree;

	@Mock
	private UserPort userPort;

	@BeforeEach
	public void setUp() {
		this.isUsernameFree = new IsUsernameFree(userPort);
	}

	@Test
	public void shouldReturnUsernameIsFree() {
	    // Arrange
		String username = "ahamaide";

		when(this.userPort.isUsernameFree(username)).thenReturn(new ActionSuccess(true));

	    // Act
		ActionSuccess actualActionSuccess = this.isUsernameFree.execute(username);

	    // Assert
	    ActionSuccess expectedActionSuccess = new ActionSuccess(true);

		assertThat(actualActionSuccess).isEqualTo(expectedActionSuccess);
	}

}