package com.sda.service;

import com.sda.exceptions.NotFoundException;
import com.sda.model.User;
import com.sda.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.function.Supplier;

/**
 */
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder encoder;

    public UserService(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    public User find(Long id) {
        return userRepository.findById(id).orElseThrow(getUserNotFoundExceptionSupplier(id));
    }

    public User findWithAddresses(Long id) {
        return userRepository.findByIdWithAddresses(id)
                .orElseThrow(getUserNotFoundExceptionSupplier(id));
    }

    public User findByEmail(final String email) {
        return userRepository.findByEmailAndEnabledTrue(email)
                .orElseThrow(() ->
                        new NotFoundException(String.format("User with email: %s not found!", email)));
    }

    private Supplier<RuntimeException> getUserNotFoundExceptionSupplier(Long id) {
        return () -> new NotFoundException(String.format("User not found: %s", id));
    }

    @Transactional
    public User save(User user) {
        String pass;

        if (user.getId() == null) {
            pass = encoder.encode(user.getPassword().trim());
        } else {
            final User oldUser = find(user.getId());
            pass = oldUser.getPassword();
            final String newPass = user.getPassword();
            if (!newPass.equals(pass)) {
                pass = encoder.encode(user.getPassword().trim());
            }
            oldUser.setLogin(user.getLogin());
            oldUser.setFirstName(user.getFirstName());
            oldUser.setLastName(user.getLastName());
            oldUser.setEmail(user.getEmail());
            oldUser.setPassword(pass);

            return oldUser;
        }

        user.setPassword(pass);
        return userRepository.save(user);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }
}
