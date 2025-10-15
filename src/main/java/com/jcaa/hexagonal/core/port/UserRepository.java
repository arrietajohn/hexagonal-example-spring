package com.jcaa.hexagonal.core.port;

import com.jcaa.hexagonal.core.domin.User;
import java.util.List;
import java.util.Optional;

public interface UserRepository {
    User save(User user);
    Optional<User> findByEmail(String email);
    Optional<User> findById(Long id);
    List<User> findAll();
}

