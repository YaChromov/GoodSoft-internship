package org.example.t10b.repository;


import io.micrometer.common.lang.NonNull;
import org.example.t10b.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByLogin(String login);
    @Override
    @NonNull
    List<User> findAll();

    default void addUser(User user) {
        save(user);
    }

    void deleteByLogin(String login);
    boolean existsByLogin(String login);
}