package org.letitgo.application.mapper;

import org.letitgo.application.dtos.ActionSuccessViewModel;
import org.letitgo.domain.beans.ActionSuccess;
import org.springframework.stereotype.Component;

@Component
public class ActionSuccessPresentationMapper {

	public ActionSuccessViewModel mapToViewModel(ActionSuccess actionSuccess) {
		return new ActionSuccessViewModel(
			actionSuccess.success(),
			actionSuccess.message().orElse(null)
		);
	}

}
