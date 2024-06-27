package org.letitgo.application.presenters;

import com.google.gson.Gson;
import org.letitgo.application.dtos.out.MemoryViewModel;
import org.letitgo.application.mappers.out.MemoryViewModelMapper;
import org.letitgo.domain.beans.Memory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MemoryPresenter {

	private final MemoryViewModelMapper memoryViewModelMapper;

	@Autowired
	public MemoryPresenter(MemoryViewModelMapper memoryViewModelMapper) {
		this.memoryViewModelMapper = memoryViewModelMapper;
	}

	public ResponseEntity<String> presentAll(List<Memory> memories) {
		List<MemoryViewModel> memoryViewModels = this.memoryViewModelMapper.mapAllToViewModel(memories);
		return ResponseEntity.ok(new Gson().toJson(memoryViewModels));
	}

}
