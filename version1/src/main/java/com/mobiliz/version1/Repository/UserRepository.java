package com.mobiliz.version1.Repository;

import com.mobiliz.version1.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Integer> {
    User findUsersByName(String name);
}

