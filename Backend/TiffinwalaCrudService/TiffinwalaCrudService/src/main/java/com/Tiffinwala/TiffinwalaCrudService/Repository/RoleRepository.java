package com.Tiffinwala.TiffinwalaCrudService.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Tiffinwala.TiffinwalaCrudService.Entities.Role;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByRoleName(String roleName);
}
