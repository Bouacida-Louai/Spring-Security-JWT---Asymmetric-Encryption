package org.springsec.springsecurityjwt.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springsec.springsecurityjwt.user.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,String> {

    boolean existsByEmailIgnoreCase(String email);

    boolean existsByPhoneNumberIgnoreCase(String phoneNumber);

    Optional<User> findByEmailIgnoreCase(String username);

}
