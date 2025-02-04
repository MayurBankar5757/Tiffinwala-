package com.Tiffinwala.TiffinwalaAuthService.Services;


import org.springframework.security.core.userdetails.User;  // Import Spring Security's User class
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import com.Tiffinwala.TiffinwalaAuthService.Exceptions.ResourceNotFoundException;
import com.Tiffinwala.TiffinwalaAuthService.Repository.UserRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public CustomUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User loadUserByUsername(String username) throws UsernameNotFoundException {
        com.Tiffinwala.TiffinwalaAuthService.Entities.User appUser = userRepository.findByEmail(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with email: " + username));

        return new User(appUser.getEmail(), appUser.getPassword(), 
                        appUser.getRole().getRoleName().equals("ADMIN") ? 
                        AuthorityUtils.createAuthorityList("ROLE_ADMIN") : 
                        AuthorityUtils.createAuthorityList("ROLE_USER"));
    }
}
