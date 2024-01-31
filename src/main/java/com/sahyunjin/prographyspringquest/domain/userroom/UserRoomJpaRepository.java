package com.sahyunjin.prographyspringquest.domain.userroom;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserRoomJpaRepository extends JpaRepository<UserRoom, Integer> {

    Optional<UserRoom> findByUserIdAndRoomId(Integer userId, Integer roomId);
    List<UserRoom> findAllByRoomId(Integer roomId);

    boolean existsByUserId(Integer userId);

    void deleteAllByRoomId(Integer roomId);
}
