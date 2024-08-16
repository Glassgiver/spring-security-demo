package com.example.springsecuritydemo.repositories;

import com.example.springsecuritydemo.entities.User;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    Optional<User> findByUsername(String username);

    @Modifying
    @Query("UPDATE User u SET u.email = :email WHERE u.id = :id")
    void updateUserEmail(@Param("email") String email, @Param("id") Long id);

    @Modifying
    @Query("DELETE FROM User u  WHERE u.id = :id")
    void deleteUser(@Param("id") Long id);

}
