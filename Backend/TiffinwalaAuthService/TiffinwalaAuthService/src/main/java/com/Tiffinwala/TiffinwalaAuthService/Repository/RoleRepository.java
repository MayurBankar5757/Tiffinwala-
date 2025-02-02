package com.Tiffinwala.TiffinwalaAuthService.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Tiffinwala.TiffinwalaAuthService.Entities.Role;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Integer> {
    Optional<Role> findByRoleName(String roleName);
}
