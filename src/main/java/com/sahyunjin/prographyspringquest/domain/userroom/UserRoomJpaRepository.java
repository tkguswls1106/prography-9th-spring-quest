package com.sahyunjin.prographyspringquest.domain.userroom;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRoomJpaRepository extends JpaRepository<UserRoom, Integer> {

    boolean existsByUserId(Integer userId);
    Optional<UserRoom> findByUserIdAndRoomId(Integer userId, Integer roomId);
    Long countByRoomId(Integer roomId);
    void deleteAllByRoomId(Integer roomId);
}
