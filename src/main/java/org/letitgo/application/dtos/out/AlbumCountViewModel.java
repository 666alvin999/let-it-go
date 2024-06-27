package org.letitgo.application.dtos.out;

import lombok.*;
import org.springframework.stereotype.Component;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class AlbumCountViewModel {

	int count;

	public static AlbumCountViewModelBuilder albumCountViewModel() {
		return new AlbumCountViewModelBuilder();
	}

}
