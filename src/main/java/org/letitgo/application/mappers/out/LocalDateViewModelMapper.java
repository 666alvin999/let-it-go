package org.letitgo.application.mappers.out;

import org.letitgo.application.dtos.out.LocalDateViewModel;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;

import static org.letitgo.application.dtos.out.LocalDateViewModel.localDateViewModel;

@Component
public class LocalDateViewModelMapper {

	private final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

	public LocalDateViewModel mapToViewModel(LocalDate date) {
		return localDateViewModel()
			.date(this.dateFormatter.format(date))
			.build();
	}

	public List<LocalDateViewModel> mapAllToViewModel(Set<LocalDate> dates) {
		return dates.stream().map(this::mapToViewModel).toList();
	}

}
