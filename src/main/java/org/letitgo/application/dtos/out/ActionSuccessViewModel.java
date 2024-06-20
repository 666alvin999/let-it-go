package org.letitgo.application.dtos.out;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class ActionSuccessViewModel {

	private boolean success;
	private String errorMessage;

}
