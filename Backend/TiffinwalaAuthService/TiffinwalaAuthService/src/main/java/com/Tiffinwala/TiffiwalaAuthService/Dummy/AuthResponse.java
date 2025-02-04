package com.Tiffinwala.TiffiwalaAuthService.Dummy;

import com.Tiffinwala.TiffinwalaAuthService.Entities.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthResponse {
    private String token;
    private Role role;  // Vendor or Customer
}

