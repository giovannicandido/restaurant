package br.com.dbserver.restaurant.core.application.usercase;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Component;

import br.com.dbserver.restaurant.core.application.dto.RestaurantListDto;
import br.com.dbserver.restaurant.core.domain.Restaurant;
import br.com.dbserver.restaurant.core.domain.repository.RestaurantRepository;
import br.com.dbserver.restaurant.core.domain.repository.VoteRepository;

@Component
public class ListRestaurantUseCase {
    private final RestaurantRepository restaurantRepository;
    private final VoteRepository voteRepository;

    public ListRestaurantUseCase(RestaurantRepository restaurantRepository, VoteRepository voteRepository) {
        this.restaurantRepository = restaurantRepository;
        this.voteRepository = voteRepository;
    }

    public List<RestaurantListDto> list() {
        List<RestaurantListDto> restaurantListDtos = new ArrayList<>();
        List<Restaurant> allRestaurants = restaurantRepository.findAllNotSelectedThisWeek();
        // todo only return votes from 00:00 to 12:00
        LocalDateTime countAfterDate = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
        LocalDateTime countBeforeDate = LocalDateTime.now().withHour(23).withMinute(0).withSecond(0);

        return allRestaurants.stream().map(r -> {
            Long vote = voteRepository.countByRestaurantAndDateTimeAfterAndDateTimeBefore(r,
                    countAfterDate, countBeforeDate);
            return new RestaurantListDto(r.getId(), r.getName(), r.getDescription(), r.getImgUrl(), vote);
        }).collect(Collectors.toList());

    }
}
