package br.com.dbserver.restaurant.core.application.usercase;

import java.time.LocalDate;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import br.com.dbserver.restaurant.core.application.dto.VoteResultListDto;
import br.com.dbserver.restaurant.core.domain.VoteResult;
import br.com.dbserver.restaurant.core.domain.repository.VoteResultRepository;
import br.com.dbserver.restaurant.core.domain.LocalizedWeek;

@Component
public class ListVoteResultUseCase {
    private final Locale LOCALE = Locale.forLanguageTag("pt_BR");
    private final VoteResultRepository voteResultRepository;

    public ListVoteResultUseCase(VoteResultRepository voteResultRepository) {
        this.voteResultRepository = voteResultRepository;
    }

    public List<VoteResultListDto> list() {
        LocalizedWeek localizedWeek = new LocalizedWeek(LOCALE);
        LocalDate startOfWeek = localizedWeek.getFirstDay(LocalDate.now());
        LocalDate endWeek = localizedWeek.getLastDay(LocalDate.now());
        List<VoteResult> allVoteResultOfWeek = voteResultRepository.findAllByDayOfResultAfterAndDayOfResultBefore(
                startOfWeek,
                endWeek);

        return allVoteResultOfWeek.stream()
                .map(voteResult -> new VoteResultListDto(voteResult.getWinRestaurant().getName(),
                        voteResult.getDayOfResult(),
                        voteResult.getNumberOfVotes()))
                .collect(Collectors.toList());

    }
}
