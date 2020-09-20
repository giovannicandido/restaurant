package br.com.dbserver.restaurant.core.application.usercase;

import java.time.LocalDateTime;
import java.util.Optional;

import org.springframework.stereotype.Component;

import br.com.dbserver.restaurant.core.domain.Restaurant;
import br.com.dbserver.restaurant.core.domain.RestaurantException;
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

    public VoteUseCase(UserRepository userRepository,
                       RestaurantRepository restaurantRepository,
                       VoteRepository voteRepository) {
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
        this.voteRepository = voteRepository;
    }

    public void vote(Long restaurantId, String username) {
        Optional<User> userByUsername = userRepository.findByUsername(username);
        if (userByUsername.isEmpty()) {
            throw new RestaurantException("User not found");
        }
        Optional<Restaurant> restaurantById = restaurantRepository.findById(restaurantId);
        if (restaurantById.isEmpty()) {
            throw new RestaurantException("Restaurant not found");
        }

        assertOneVotePerUserPerDay(userByUsername.get());

        // todo validate time of vote and one vote per user per day
        Vote vote = new Vote();
        vote.setUser(userByUsername.get());
        vote.setRestaurant(restaurantById.get());
        vote.setDateTime(LocalDateTime.now());
        voteRepository.save(vote);
    }

    public void assertOneVotePerUserPerDay(User user) {
        LocalDateTime countAfterDate = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
        LocalDateTime countBeforeDate = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59);
        if (voteRepository.countByUserAndDateTimeAfterAndDateTimeBefore(user, countAfterDate,
                countBeforeDate) >= 1) {
            throw new RestaurantException("Vote already computed for this user today");
        }
    }
}
