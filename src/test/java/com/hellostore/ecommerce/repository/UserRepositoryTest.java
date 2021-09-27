package com.hellostore.ecommerce.repository;

import com.hellostore.ecommerce.entity.User;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.util.Optional;

@SpringBootTest
@Transactional
@Rollback(value = false)
@Slf4j
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    public void passwordMatchTest() {
        Optional<User> user = userRepository.findByUsername("hkpark");

        boolean matches = passwordEncoder.matches("1234", user.get().getPassword());
        log.debug("user.get().getPassword():{}, matches: {}", user.get().getPassword(), matches);
    }
}
