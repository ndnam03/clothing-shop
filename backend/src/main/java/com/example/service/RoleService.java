package com.example.service;

import com.example.entity.Role;

import java.util.Optional;

public interface RoleService {

    Optional<Role> findByRoleName(String roleName);
}
