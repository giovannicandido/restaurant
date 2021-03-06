package br.com.dbserver.restaurant.core.application.usercase;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import br.com.dbserver.restaurant.core.domain.Restaurant;
import br.com.dbserver.restaurant.core.domain.RestaurantException;
import br.com.dbserver.restaurant.core.domain.Vote;
import br.com.dbserver.restaurant.core.domain.repository.RestaurantRepository;
import br.com.dbserver.restaurant.core.domain.repository.VoteRepository;
import br.com.dbserver.restaurant.core.domain.service.VoteTimeAllowedService;
import br.com.dbserver.restaurant.security.domain.User;
import br.com.dbserver.restaurant.security.domain.repository.UserRepository;

@Component
public class VoteUseCase {
    private final UserRepository userRepository;
    private final RestaurantRepository restaurantRepository;
    private final VoteRepository voteRepository;
    private final VoteTimeAllowedService voteTimeAllowedService;

    public VoteUseCase(UserRepository userRepository,
                       RestaurantRepository restaurantRepository,
                       VoteRepository voteRepository,
                       VoteTimeAllowedService voteTimeAllowedService) {
        this.userRepository = userRepository;
        this.restaurantRepository = restaurantRepository;
        this.voteRepository = voteRepository;
        this.voteTimeAllowedService = voteTimeAllowedService;
    }

    @Transactional
    public void vote(Long restaurantId, String username) {
        assertTimeAllowed();
        Optional<User> userByUsername = userRepository.findByUsername(username);

        assertUserExists(userByUsername);

        Optional<Restaurant> restaurantById = restaurantRepository.findById(restaurantId);

        assertRestaurantExists(restaurantById);

        User user = userByUsername.get();
        Restaurant restaurant = restaurantById.get();

        assertOneVotePerUserPerDay(user);

        Vote vote = new Vote();
        vote.setUser(user);
        vote.setRestaurant(restaurant);
        vote.setDateTime(LocalDateTime.now());
        voteRepository.save(vote);
    }

    private void assertRestaurantExists(Optional<Restaurant> restaurantById) {
        if (restaurantById.isEmpty()) {
            throw new RestaurantException("Restaurant not found");
        }
    }

    private void assertUserExists(Optional<User> userByUsername) {
        if (userByUsername.isEmpty()) {
            throw new RestaurantException("User not found");
        }
    }

    private void assertTimeAllowed() {
        if (!voteTimeAllowedService.isTimeAllowed()) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

            throw new RestaurantException("You cannot vote at this time. "
                    + "you can vote between "
                    + voteTimeAllowedService.getStartTime().format(formatter)
                    + " and " + voteTimeAllowedService.getFinishTime().format(formatter)
            );
        }
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
