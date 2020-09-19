package br.com.dbserver.restaurant.core.application.usercase;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Component;

import br.com.dbserver.restaurant.core.domain.Restaurant;
import br.com.dbserver.restaurant.core.domain.Vote;
import br.com.dbserver.restaurant.core.domain.repository.RestaurantRepository;
import br.com.dbserver.restaurant.core.domain.repository.VoteRepository;
import br.com.dbserver.restaurant.security.domain.User;
import br.com.dbserver.restaurant.security.domain.repository.UserRepository;

@Component
public class VoteUseCase {
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final VoteRepository voteRepository;

    public VoteUseCase(UserRepository userRepository, RestaurantRepository restaurantRepository, VoteRepository voteRepository) {
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
        this.voteRepository = voteRepository;
    }

    public void vote(Long restaurantId, String username) {
        Optional<User> userByUsername = userRepository.findByUsername(username);
        if (userByUsername.isEmpty()) {
            throw new RuntimeException("User not found");
        }
        Optional<Restaurant> restaurantById = restaurantRepository.findById(restaurantId);
        if (restaurantById.isEmpty()) {
            throw new RuntimeException("Restaurant not found");
        }
        // todo validate time of vote and one vote per user per day
        Vote vote = new Vote();
        vote.setUser(userByUsername.get());
        vote.setRestaurant(restaurantById.get());
        vote.setDateTime(LocalDateTime.now());
        voteRepository.save(vote);
    }
}
