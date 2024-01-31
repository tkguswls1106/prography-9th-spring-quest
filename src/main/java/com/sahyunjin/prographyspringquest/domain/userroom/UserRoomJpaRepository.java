package com.sahyunjin.prographyspringquest.domain.userroom;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRoomJpaRepository extends JpaRepository<UserRoom, Integer> {

    boolean existsByUserId(Integer userId);
    Long countByRoomId(Integer roomId);
}
