package br.com.dbserver.restaurant.core.domain.service;

import static org.mockito.Mockito.verify;

import java.time.LocalDate;
import java.util.Locale;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import br.com.dbserver.restaurant.core.domain.LocalizedWeek;
import br.com.dbserver.restaurant.core.domain.repository.VoteResultRepository;

@ExtendWith(MockitoExtension.class)
class RestaurantServiceImplTest {
    @Mock
    private VoteResultRepository voteResultRepository;

    @Test
    void findRestaurantIdsVotedThisWeek() {
        RestaurantServiceImpl restaurantService = new RestaurantServiceImpl(voteResultRepository);
        LocalizedWeek localizedWeek = new LocalizedWeek(Locale.forLanguageTag("pt_BR"));
        restaurantService.findRestaurantIdsNotChoosedThisWeek();

        verify(voteResultRepository).findAllByDayOfResultAfterAndDayOfResultBefore(
                localizedWeek.getFirstDay(LocalDate.now()),
                localizedWeek.getLastDay(LocalDate.now()));
    }
}
