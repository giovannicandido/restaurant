package br.com.dbserver.restaurant.core.domain.service;

import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

@Service
public class VoteTimeAllowedServiceImpl implements VoteTimeAllowedService {

    @Override
    public boolean isTimeAllowed() {
        LocalDateTime today = LocalDateTime.now();
        LocalDateTime start = getStartTime();
        LocalDateTime finish = getFinishTime();
        return today.isAfter(start) && today.isBefore(finish);
    }

    @Override
    public LocalDateTime getStartTime() {
        return LocalDateTime.now().withHour(8).withMinute(0).withSecond(0);
    }

    @Override
    public LocalDateTime getFinishTime() {
        return LocalDateTime.now().withHour(12).withMinute(0).withSecond(0);
    }
}
