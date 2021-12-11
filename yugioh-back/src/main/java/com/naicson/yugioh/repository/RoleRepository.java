package com.naicson.yugioh.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.naicson.yugioh.entity.auth.ERole;
import com.naicson.yugioh.entity.auth.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Integer>{
	
	Optional<Role> findByRoleName(ERole name);
}
