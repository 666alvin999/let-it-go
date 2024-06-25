package org.letitgo.domain.beans.userfields;

import lombok.Getter;

@Getter
public enum ColorTheme {

	URANUS("uranus"),
	SATURN("saturn"),
	JUPITER("jupiter"),
	VENUS("venus"),
	NULL("null");

	private final String value;

	ColorTheme(String value) {
		this.value = value;
	}
}
