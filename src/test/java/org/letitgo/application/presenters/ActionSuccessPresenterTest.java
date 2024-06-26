package org.letitgo.application.presenters;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.letitgo.application.dtos.out.ActionSuccessViewModel;
import org.letitgo.application.mappers.out.ActionSuccessViewModelMapper;
import org.letitgo.domain.beans.ActionSuccess;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ActionSuccessPresenterTest {

	private ActionSuccessPresenter presenter;

	@Mock
	private ActionSuccessViewModelMapper actionSuccessViewModelMapper;

	@BeforeEach
	public void setUp() {
		this.presenter = new ActionSuccessPresenter(actionSuccessViewModelMapper);
	}

	@Test
	public void shouldPresentSuccessfullAction() {
		// Arrange
		ActionSuccess actionSuccess = new ActionSuccess(true);
		ActionSuccessViewModel actionSuccessViewModel = new ActionSuccessViewModel(true, null);

		when(this.actionSuccessViewModelMapper.mapToViewModel(actionSuccess)).thenReturn(actionSuccessViewModel);

		// Act
		ResponseEntity<String> actualResponse = this.presenter.present(actionSuccess);

		// Assert
		ResponseEntity<String> expectedResponse = ResponseEntity.ok("{\"success\":true}");

		assertThat(actualResponse).isEqualTo(expectedResponse);
	}

	@Test
	public void shouldPresentFailedAction() {
		// Arrange
		ActionSuccess actionSuccess = new ActionSuccess(false, Optional.ofNullable("error"));
		ActionSuccessViewModel actionSuccessViewModel = new ActionSuccessViewModel(false, "error");

		when(this.actionSuccessViewModelMapper.mapToViewModel(actionSuccess)).thenReturn(actionSuccessViewModel);

		// Act
		ResponseEntity<String> actualResponse = this.presenter.present(actionSuccess);

		// Assert
		ResponseEntity<String> expectedResponse = ResponseEntity.badRequest().body("{\"success\":false,\"errorMessage\":\"error\"}");

		assertThat(actualResponse).isEqualTo(expectedResponse);
	}

}