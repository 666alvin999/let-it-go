package org.letitgo.application.presenters;

import com.google.gson.Gson;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.letitgo.application.dtos.ActionSuccessViewModel;
import org.letitgo.application.mapper.ActionSuccessPresentationMapper;
import org.letitgo.domain.beans.ActionSuccess;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
@AllArgsConstructor
public class ActionSuccessPresenter {

	private ActionSuccessPresentationMapper actionSuccessPresentationMapper;

	public ResponseEntity<String> present(ActionSuccess actionSuccess) {
		ActionSuccessViewModel actionSuccessViewModel = this.actionSuccessPresentationMapper.mapToViewModel(actionSuccess);

		if (actionSuccess.success()) {
			return ResponseEntity.ok(new Gson().toJson(actionSuccessViewModel));
		} else {
			return ResponseEntity.badRequest().body(new Gson().toJson(actionSuccessViewModel));
		}
	}

}
