package br.com.dbserver.restaurant.security.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.dbserver.restaurant.security.domain.ERole;
import br.com.dbserver.restaurant.security.domain.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
