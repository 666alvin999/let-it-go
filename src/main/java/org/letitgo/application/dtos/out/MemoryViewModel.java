package org.letitgo.application.dtos.out;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class MemoryViewModel {

	private String content;
	private String date;
	private String mediaName;
	private String mood;

	public static MemoryViewModelBuilder memoryViewModel() {
		return new MemoryViewModelBuilder();
	}

}
