package Tiffinwala.App.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import Tiffinwala.App.Entities.Role;
import Tiffinwala.App.Services.RoleServices;

@RestController


public class RoleController {

    @Autowired
    private RoleServices roleService;

    // Create a new role
    @PostMapping
    public Role createRole(@RequestBody Role role) {
        return roleService.createRole(role);
    }

    // Retrieve all roles
    @GetMapping("/getRoles")
    public List<Role> getAllRoles() {
        return roleService.getAllRoles();
    }

    // Retrieve a role by ID
    @GetMapping("/{id}")
    public Role getRoleById(@PathVariable Integer id) {
        return roleService.getRoleById(id);
    }

    // Update a role
    @PutMapping("/{id}")
    public Role updateRole(@PathVariable Integer id, @RequestBody Role role) {
        return roleService.updateRole(id, role);
    }

    // Delete a role
    @DeleteMapping("/{id}")
    public void deleteRole(@PathVariable Integer id) {
        roleService.deleteRole(id);
    }
}