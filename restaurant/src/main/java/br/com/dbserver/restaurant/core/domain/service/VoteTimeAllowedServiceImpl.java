package br.com.dbserver.restaurant.core.domain.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * Service used to query allowed time frames of voting process.
 * This implementation only allow voting from 8 to 12 by default
 */
@Service
public class VoteTimeAllowedServiceImpl implements VoteTimeAllowedService {

    @Value("${restaurant.vote.startHour}")
    private Integer startHour;
    @Value("${restaurant.vote.finishHour}")
    private Integer finishHour;

    @Override
    public boolean isTimeAllowed() {
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime start = getStartTime();
        LocalDateTime finish = getFinishTime();
        return today.isAfter(start) && today.isBefore(finish);
    }

    @Override
    public LocalDateTime getStartTime() {
        return LocalDateTime.now().withHour(startHour).withMinute(0).withSecond(0);
    }

    @Override
    public LocalDateTime getFinishTime() {
        return LocalDateTime.now().withHour(finishHour).withMinute(0).withSecond(0);
    }
}
