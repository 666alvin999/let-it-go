package org.letitgo.domain.usecases;

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
class IsMailFreeTest {

	private IsMailFree isMailFree;

	@Mock
	private UserPort userPort;

	@BeforeEach
	public void setUp() {
		this.isMailFree = new IsMailFree(userPort);
	}

	@Test
	public void shouldReturnMailIsFree() {
	    // Arrange
		String mail = "mail";

		when(this.userPort.isMailFree(mail)).thenReturn(new ActionSuccess(true));

	    // Act
		ActionSuccess actualActionSuccess = this.isMailFree.execute(mail);

	    // Assert
	    ActionSuccess expectedActionSuccess = new ActionSuccess(true);

		assertThat(actualActionSuccess).isEqualTo(expectedActionSuccess);
	}

}