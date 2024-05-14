package com.cydeo.repository;

import com.cydeo.dto.UserDTO;
import com.cydeo.entity.User;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

public interface UserRepository extends JpaRepository<User, Long> {
    User findByUserName(String username);

    @Transactional
    void deleteByUserName(String userName);

    @Query("SELECT u FROM User u WHERE u.role = (SELECT r FROM Role r WHERE r.description='Manager' ) ORDER BY u.lastName")
    List<User> findManagers();
    @Query("SELECT u FROM User u WHERE u.role = (SELECT r FROM Role r WHERE r.description='Employee' ) ORDER BY u.lastName")
    List<User> findEmployees();
}
