package com.hdsinh.cardealer.repository.User;

import com.hdsinh.cardealer.entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long>, UserRepositoryCustom {
    Optional<Users> findByUsername(String username);
}
