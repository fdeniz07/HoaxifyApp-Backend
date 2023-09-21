package com.hoaxify.ws.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {

    //DB de kayitli email ve username var mi?
    User findByEmail(String email);
//    User findByUsername(String username);
}
