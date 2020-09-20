package br.com.dbserver.restaurant.core.domain.service;

import java.time.LocalDateTime;

public interface VoteTimeAllowedService {
    boolean isTimeAllowed();
    LocalDateTime getStartTime();

    LocalDateTime getFinishTime();
}
