package org.example.t10b.repository;

import io.micrometer.common.lang.NonNull;

import org.example.t10b.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

    Optional<Role> findByName(String name);

    Set<Role> findAllByNameIn(Collection<String> names);

    @Override
    @NonNull
    List<Role> findAll();
}