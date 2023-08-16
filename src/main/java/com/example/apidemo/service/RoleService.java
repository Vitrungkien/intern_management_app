package com.example.apidemo.service;

import com.example.apidemo.models.ERole;
import com.example.apidemo.models.Roles;

import java.util.Optional;

public interface RoleService {
    Optional<Roles> findByRoleName(ERole roleName);
}
