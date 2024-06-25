package org.letitgo.application.dtos.in;

import lombok.*;
import org.springframework.web.multipart.MultipartFile;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@EqualsAndHashCode
@Builder
public class MemoryForm {

	private MultipartFile multipartFile;
	private String fileName;
	private String username;
	private String albumName;
	private String content;
	private String mood;
	private String memoryDatetime;

	public static MemoryFormBuilder saveMemoryForm() {
		return new MemoryFormBuilder();
	}

}
