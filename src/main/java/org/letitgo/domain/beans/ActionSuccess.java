package org.letitgo.domain.beans;

import java.util.Optional;

public record ActionSuccess(boolean success, Optional<String> message) {

	public ActionSuccess(boolean success) {
		this(success, Optional.empty());
	}

}
