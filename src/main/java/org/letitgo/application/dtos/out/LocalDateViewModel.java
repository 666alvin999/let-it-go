package org.letitgo.application.dtos.out;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class LocalDateViewModel {

	private String date;

	public static LocalDateViewModelBuilder localDateViewModel() {
		return new LocalDateViewModelBuilder();
	}

}
