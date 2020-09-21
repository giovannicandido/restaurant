package br.com.dbserver.restaurant.core.domain.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import br.com.dbserver.restaurant.core.domain.VoteResult;

public interface VoteResultRepository extends CrudRepository<VoteResult, Long> {
    List<VoteResult> findAllByDayOfResultAfterAndDayOfResultBefore(LocalDate afterDate,
                                                                   LocalDate beforeDate);
}
