package br.com.dbserver.restaurant.core.domain.service;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.dbserver.restaurant.core.domain.VoteResult;
import br.com.dbserver.restaurant.core.domain.repository.VoteResultRepository;

@Service
public class RestaurantServiceImpl implements RestaurantService {
    private final VoteResultRepository repository;

    public RestaurantServiceImpl(VoteResultRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<Long> findRestaurantIdsVotedThisWeek() {
        LocalizedWeek localizedWeek = new LocalizedWeek(Locale.forLanguageTag("pt_BR"));
        LocalDate startOfWeek = localizedWeek.getFirstDay(LocalDate.now());
        LocalDate endOfWeek = localizedWeek.getLastDay(LocalDate.now());
        return repository.findAllByDayOfResultAfterAndDayOfResultBefore(startOfWeek, endOfWeek)
                .stream()
                .map(VoteResult::getId)
                .collect(Collectors.toList());
    }
}
