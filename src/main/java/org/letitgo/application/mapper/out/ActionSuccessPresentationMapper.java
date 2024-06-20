package org.letitgo.application.mapper.out;

import org.letitgo.application.dtos.out.ActionSuccessViewModel;
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
