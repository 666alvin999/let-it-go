package org.letitgo.infrastructure.dtos;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class MemoryDTO {

	private String albumName;
	private String username;
	private String textContent;
	private String mediaName;
	private String memoryDatetime;
	private String mood;

	public static MemoryDTOBuilder memoryDTO() {
		return new MemoryDTOBuilder();
	}

}
