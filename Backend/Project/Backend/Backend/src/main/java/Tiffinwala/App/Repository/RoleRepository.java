package Tiffinwala.App.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import Tiffinwala.App.Entities.Role;
import Tiffinwala.App.Entities.User;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer> {

	Optional<User> findByRoleName(String string);
	
}

