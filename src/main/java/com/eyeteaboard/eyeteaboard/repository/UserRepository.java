package com.eyeteaboard.eyeteaboard.repository;

import com.eyeteaboard.eyeteaboard.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {

  Optional<User> findByAuthKey(String uuid);
}
