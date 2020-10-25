package com.sda.repository;

import com.sda.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

/**
 */
public interface UserRepository extends JpaRepository<User, Long> {
    @Query("select u from User u " +
            " left join fetch u.addresses " +
            " where u.id = :user_id")
    User findByIdWithAddresses(@Param("user_id") Long id);
}
