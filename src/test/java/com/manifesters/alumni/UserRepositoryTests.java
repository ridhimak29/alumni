package com.manifesters.alumni;

import com.manifesters.alumni.types.User;
import com.manifesters.alumni.dao.UserRepository;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class UserRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private UserRepository repo;

    @Test
    public void  testCreateUser(){
        User user =new User();
        user.setEmail("abc@gmail.com");
        user.setPassword("abc@123");
        user.setFirstname("ab");
        user.setLastName("ca");
        user.setRole("alumni_coordinator");
        User savedUser= repo.save(user);
        User existUser=entityManager.find(User.class,savedUser.getId());
        assertThat(user.getEmail()).isEqualTo(existUser.getEmail());
    }
    @Test
    public void testFindUserByEmail(){
        String email="demo1@hotmail.com";
        User user=repo.findByEmail(email);
        assertThat(user).isNotNull();

    }

}
