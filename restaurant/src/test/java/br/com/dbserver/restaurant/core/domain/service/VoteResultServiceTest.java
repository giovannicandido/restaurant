package br.com.dbserver.restaurant.core.domain.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.dbserver.restaurant.core.domain.Restaurant;
import br.com.dbserver.restaurant.core.domain.VoteResult;
import br.com.dbserver.restaurant.core.domain.repository.VoteRepository;
import br.com.dbserver.restaurant.core.domain.repository.VoteResultRepository;

@ExtendWith(MockitoExtension.class)
class VoteResultServiceTest {
    @Mock
    private VoteResultRepository voteResultRepository;
    @Mock
    private VoteRepository voteRepository;

    @Test
    void processVoteOfThisDay() {

        VoteResultService voteResultService = new VoteResultService(voteResultRepository, voteRepository);

        Restaurant r1 = new Restaurant();
        r1.setId(1L);
        Restaurant r2 = new Restaurant();
        r2.setId(2L);
        List<RestaurantSumVotes> mockedListOfRestaurantSumVotes = List.of(
                new RestaurantSumVotes(r1, 4L),
                new RestaurantSumVotes(r2, 6L));

        when(voteRepository.sumAllVotesByPeriod(any(LocalDateTime.class), any(LocalDateTime.class)))
                .thenReturn(mockedListOfRestaurantSumVotes);

        voteResultService.processVoteOfThisDay();

        VoteResult expectedVoteResult = new VoteResult();
        expectedVoteResult.setNumberOfVotes(6L);
        expectedVoteResult.setWinRestaurant(r2);
        expectedVoteResult.setDayOfResult(LocalDate.now());

        verify(voteResultRepository).save(expectedVoteResult);

    }
}
