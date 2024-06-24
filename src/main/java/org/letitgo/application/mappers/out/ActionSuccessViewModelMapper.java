package org.letitgo.application.mappers.out;

import org.letitgo.application.dtos.out.ActionSuccessViewModel;
import org.letitgo.domain.beans.ActionSuccess;
import org.springframework.stereotype.Component;

@Component
public class ActionSuccessViewModelMapper {

	public ActionSuccessViewModel mapToViewModel(ActionSuccess actionSuccess) {
		return new ActionSuccessViewModel(
			actionSuccess.success(),
			actionSuccess.message().orElse(null)
		);
	}

}
