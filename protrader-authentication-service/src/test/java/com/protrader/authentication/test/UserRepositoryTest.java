package com.protrader.authentication.test;

import com.protrader.authentication.entity.UserEntity;
import com.protrader.authentication.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSaveUser() {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("test@example.com");
        userEntity.setPassword("password123");

        UserEntity savedUserEntity = userRepository.save(userEntity);
        assertNotNull(savedUserEntity.getId());
    }

    @Test
    public void testFindUserByEmail() {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("findme@example.com");
        userEntity.setPassword("findpassword");

        userRepository.save(userEntity);
        Optional<UserEntity> foundUser = userRepository.findByEmail("findme@example.com");

        assertTrue(foundUser.isPresent());
        assertEquals("findme@example.com", foundUser.get().getEmail());
    }

    @Test
    public void testDeleteUser() {
        UserEntity userEntity = new UserEntity();
        userEntity.setEmail("delete@example.com");
        userEntity.setPassword("deletepassword");

        UserEntity savedUserEntity = userRepository.save(userEntity);
        userRepository.deleteById(savedUserEntity.getId());

        Optional<UserEntity> deletedUser = userRepository.findById(savedUserEntity.getId());
        assertFalse(deletedUser.isPresent());
    }
}
