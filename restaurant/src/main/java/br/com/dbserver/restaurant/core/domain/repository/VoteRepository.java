package br.com.dbserver.restaurant.core.domain.repository;

import org.springframework.data.repository.CrudRepository;

import br.com.dbserver.restaurant.core.domain.Vote;

public interface VoteRepository extends CrudRepository<Vote, Long> {
}
