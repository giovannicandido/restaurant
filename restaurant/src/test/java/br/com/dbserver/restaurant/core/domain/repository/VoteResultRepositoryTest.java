package br.com.dbserver.restaurant.core.domain.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.jdbc.Sql;

import br.com.dbserver.restaurant.core.domain.VoteResult;

@DataJpaTest
@Sql({"/sql/clear-database.sql","/sql/list-restaurant.sql","/sql/votes.sql", "/sql/vote_result.sql"})
class VoteResultRepositoryTest {
    @Autowired
    private VoteResultRepository repository;

    @Test
    void findAllByDayOfResultAfterAndDayOfResultBefore() {
        LocalDate afterDate = LocalDate.of(2020, 9, 18);
        LocalDate endDate = LocalDate.of(2020, 9, 21);
        List<VoteResult> resultList = repository.findAllByDayOfResultAfterAndDayOfResultBefore(afterDate, endDate);
        assertThat(resultList).hasSize(2);
    }
}
