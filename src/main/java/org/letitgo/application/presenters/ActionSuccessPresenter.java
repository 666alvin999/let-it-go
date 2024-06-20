package org.letitgo.application.presenters;

import com.google.gson.Gson;
import lombok.NoArgsConstructor;
import org.letitgo.application.dtos.out.ActionSuccessViewModel;
import org.letitgo.application.mappers.out.ActionSuccessPresentationMapper;
import org.letitgo.domain.beans.ActionSuccess;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class ActionSuccessPresenter {

	private ActionSuccessPresentationMapper actionSuccessPresentationMapper;

	@Autowired
	public ActionSuccessPresenter(ActionSuccessPresentationMapper actionSuccessPresentationMapper) {
		this.actionSuccessPresentationMapper = actionSuccessPresentationMapper;
	}

	public ResponseEntity<String> present(ActionSuccess actionSuccess) {
		ActionSuccessViewModel actionSuccessViewModel = this.actionSuccessPresentationMapper.mapToViewModel(actionSuccess);

		if (actionSuccess.success()) {
			return ResponseEntity.ok(new Gson().toJson(actionSuccessViewModel));
		} else {
			return ResponseEntity.badRequest().body(new Gson().toJson(actionSuccessViewModel));
		}
	}

}
