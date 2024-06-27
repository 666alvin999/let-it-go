package org.letitgo.application.mappers.out;

import org.letitgo.application.dtos.out.MemoryViewModel;
import org.letitgo.domain.beans.Memory;
import org.springframework.stereotype.Component;

import java.time.format.DateTimeFormatter;
import java.util.List;

@Component
public class MemoryViewModelMapper {

	private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	public MemoryViewModel mapToViewModel(Memory memory) {
		return new MemoryViewModel(
			memory.content().value(),
			this.dateFormatter.format(memory.memoryDatetime().value()),
			memory.mediaName().value(),
			memory.mood().getValue()
		);
	}

	public List<MemoryViewModel> mapAllToViewModel(List<Memory> memories) {
		return memories.stream().map(this::mapToViewModel).toList();
	}

}
