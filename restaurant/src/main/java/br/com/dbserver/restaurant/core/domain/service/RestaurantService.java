package br.com.dbserver.restaurant.core.domain.service;

import java.util.List;

public interface RestaurantService {
    List<Long> findRestaurantIdsVotedThisWeek();
}
