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
import br.com.dbserver.restaurant.core.domain.service.VoteTimeAllowedService;

@Component
public class ListRestaurantUseCase {
    private final RestaurantRepository restaurantRepository;
    private final VoteRepository voteRepository;
    private final VoteTimeAllowedService voteTimeAllowedService;

    public ListRestaurantUseCase(RestaurantRepository restaurantRepository, VoteRepository voteRepository, VoteTimeAllowedService voteTimeAllowedService) {
        this.restaurantRepository = restaurantRepository;
        this.voteRepository = voteRepository;
        this.voteTimeAllowedService = voteTimeAllowedService;
    }

    public List<RestaurantListDto> list() {
        List<RestaurantListDto> restaurantListDtos = new ArrayList<>();
        List<Restaurant> allRestaurants = restaurantRepository.findAllNotSelectedThisWeek();

        LocalDateTime countAfterDate = voteTimeAllowedService.getStartTime();
        LocalDateTime countBeforeDate = voteTimeAllowedService.getFinishTime();

        return allRestaurants.stream().map(r -> {
            Long vote = voteRepository.countByRestaurantAndDateTimeAfterAndDateTimeBefore(r,
                    countAfterDate, countBeforeDate);
            return new RestaurantListDto(r.getId(), r.getName(), r.getDescription(), r.getImgUrl(), vote);
        }).collect(Collectors.toList());

    }
}
