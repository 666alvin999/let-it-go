package org.letitgo.application.presenters;

import com.google.gson.Gson;
import org.letitgo.application.dtos.out.AlbumViewModel;
import org.letitgo.application.dtos.out.LocalDateViewModel;
import org.letitgo.application.mappers.out.AlbumViewModelMapper;
import org.letitgo.application.mappers.out.LocalDateViewModelMapper;
import org.letitgo.domain.beans.Album;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

@Component
public class LocalDatePresenter {

	private final LocalDateViewModelMapper localDateViewModelMapper;

	@Autowired
	public LocalDatePresenter(LocalDateViewModelMapper localDateViewModelMapper) {
		this.localDateViewModelMapper = localDateViewModelMapper;
	}

	public ResponseEntity<String> present(LocalDate localDate) {
		LocalDateViewModel localDateViewModel = this.localDateViewModelMapper.mapToViewModel(localDate);
		return ResponseEntity.ok(new Gson().toJson(localDateViewModel));
	}

	public ResponseEntity<String> presentAll(Set<LocalDate> localDates) {
		List<LocalDateViewModel> localDateViewModels = this.localDateViewModelMapper.mapAllToViewModel(localDates);
		return ResponseEntity.ok(new Gson().toJson(localDateViewModels));
	}
}
