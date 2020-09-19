package br.com.dbserver.restaurant.core.framework.api;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.dbserver.restaurant.core.application.dto.RestaurantListDto;
import br.com.dbserver.restaurant.core.application.usercase.ListRestaurantUseCase;

@RestController
@RequestMapping("${v1Api}/restaurant")
public class RestaurantController {
    private final ListRestaurantUseCase listRestaurantUseCase;

    public RestaurantController(ListRestaurantUseCase listRestaurantUseCase) {
        this.listRestaurantUseCase = listRestaurantUseCase;
    }

    @GetMapping
    public List<RestaurantListDto> listRestaurants() {
        return listRestaurantUseCase.list();
    }
}
