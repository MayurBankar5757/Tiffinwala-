package Tiffinwala.App.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import Tiffinwala.App.Entities.Role;
import Tiffinwala.App.Repository.RoleRepository;

@Service
public class RoleServices {

    @Autowired
    private RoleRepository roleRepository;

    // Create a new role
    public Role createRole(Role role) {
        return roleRepository.save(role);
    }

    // Retrieve all roles
    public List<Role> getAllRoles() {
        return roleRepository.findAll();
    }

    // Retrieve a role by ID
    public Role getRoleById(Integer id) {
        return roleRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Role not found with ID: " + id));
    }

    // Update a role by ID
    public Role updateRole(Integer id, Role updatedRole) {
        Role existingRole = getRoleById(id);
        existingRole.setRoleName(updatedRole.getRoleName());
        return roleRepository.save(existingRole);
    }

    // Delete a role by ID
    public void deleteRole(Integer id) {
        roleRepository.deleteById(id);
    }
}

