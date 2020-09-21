package br.com.dbserver.restaurant.core.domain.service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import br.com.dbserver.restaurant.core.domain.VoteResult;
import br.com.dbserver.restaurant.core.domain.repository.VoteRepository;
import br.com.dbserver.restaurant.core.domain.repository.VoteResultRepository;

@Service
public class VoteResultService {
    private final Logger logger = LoggerFactory.getLogger(VoteResultService.class);
    private final VoteResultRepository voteResultRepository;
    private final VoteRepository voteRepository;

    public VoteResultService(VoteResultRepository voteResultRepository,
                             VoteRepository voteRepository) {
        this.voteResultRepository = voteResultRepository;
        this.voteRepository = voteRepository;
    }

    @Scheduled(cron = "${restaurant.vote.processCron}")
    public void processVoteOfThisDay() {
        deleteVoteResultForThisDayIfAny();
        List<RestaurantSumVotes> votes = getAllVotesForThisDay();
        RestaurantSumVotes winner = findTheWinner(votes);
        saveVoteResult(winner);
    }

    private void deleteVoteResultForThisDayIfAny() {
        LocalDate after = LocalDate.now().minusDays(1);
        LocalDate before = LocalDate.now().plusDays(1);
        List<VoteResult> votes = voteResultRepository.findAllByDayOfResultAfterAndDayOfResultBefore(after, before);
        voteResultRepository.deleteAll(votes);
    }

    private void saveVoteResult(RestaurantSumVotes winner) {
        if (winner != null) {
            VoteResult voteResult = new VoteResult();
            voteResult.setDayOfResult(LocalDate.now());
            voteResult.setNumberOfVotes(winner.getNumberOfVotes());
            voteResult.setWinRestaurant(winner.getRestaurant());

            voteResultRepository.save(voteResult);
        } else {
            logger.info("No vote result for today");
        }
    }

    private RestaurantSumVotes findTheWinner(List<RestaurantSumVotes> votes) {
        long maxResult = 0;
        RestaurantSumVotes maxRestaurant = null;

        for (RestaurantSumVotes vote : votes) {
            if (vote.getNumberOfVotes() > maxResult) {
                maxResult = vote.getNumberOfVotes();
                maxRestaurant = vote;
            }
        }
        return maxRestaurant;
    }

    private List<RestaurantSumVotes> getAllVotesForThisDay() {
        LocalDateTime startDate = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0);
        LocalDateTime endDate = LocalDateTime.now().withHour(23).withMinute(59).withSecond(59);

        return voteRepository.sumAllVotesByPeriod(startDate, endDate);
    }
}
