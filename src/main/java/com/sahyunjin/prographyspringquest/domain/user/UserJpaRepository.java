package com.sahyunjin.prographyspringquest.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserJpaRepository extends JpaRepository<User, Integer> {
}
