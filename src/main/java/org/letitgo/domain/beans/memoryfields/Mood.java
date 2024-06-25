package org.letitgo.domain.beans.memoryfields;

import lombok.Getter;

@Getter
public enum Mood {

	HAPPY("happy"),
	PROUD("proud"),
	EXCITED("excited"),
	CONFIDENT("confident"),
	STRESSED("stressed"),
	ANGRY("angry"),
	SAD("sad"),
	TIRED("tired");

	private final String value;

	Mood(String value) {
		this.value = value;
	}

}
