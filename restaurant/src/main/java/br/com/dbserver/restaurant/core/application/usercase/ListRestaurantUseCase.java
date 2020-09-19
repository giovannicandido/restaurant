package br.com.dbserver.restaurant.core.application.usercase;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import br.com.dbserver.restaurant.core.application.dto.RestaurantListDto;
import br.com.dbserver.restaurant.core.domain.Restaurant;
import br.com.dbserver.restaurant.core.domain.repository.RestaurantRepository;

@Component
public class ListRestaurantUseCase {
    private final RestaurantRepository restaurantRepository;

    public ListRestaurantUseCase(RestaurantRepository restaurantRepository) {
        this.restaurantRepository = restaurantRepository;
    }

    public List<RestaurantListDto> list() {
        List<RestaurantListDto> restaurantListDtos = new ArrayList<>();

        Iterable<Restaurant> all = restaurantRepository.findAll();

        all.forEach(restaurant -> restaurantListDtos.add(new RestaurantListDto(restaurant.getId(),
                restaurant.getName(), restaurant.getDescription(), restaurant.getImgUrl(), 10)));

        return restaurantListDtos;
    }
}
