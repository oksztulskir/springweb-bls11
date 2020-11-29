package com.sda.service;

import com.sda.exceptions.NotFoundException;
import com.sda.model.User;
import com.sda.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

/**
 */
@ExtendWith(MockitoExtension.class)
class UserServiceTest {
    private static final long ID = 1L;
    private static final String EMAIL = "myemail@gmail.com";
    private static final String LOGIN = "mylogin";
    private static final String PASSWORD = "password";
    private static final String FIRST_NAME = "Jan";
    private static final String LAST_NAME = "Kowalski";
    private static final String PASSWORD_2 = "password_2";
    private static final String FIRST_NAME_2 = "Janek";
    private static final String LAST_NAME_2 = "Kowalski2";

    @Mock
    private PasswordEncoder encoder;
    @Mock
    private UserRepository repository;
    @InjectMocks
    private UserService service;

    @Test
    void testFindById() {
        // given
        User user = new User();
        user.setEmail("myemail@gmail.com");
        user.setLogin("myuser");
        ArgumentCaptor<Long> idCapture = ArgumentCaptor.forClass(Long.class);
        when(repository.findById(ID)).thenReturn(Optional.of(user));

        // when
        User returned = service.find(ID);

        // then
        verify(repository).findById(idCapture.capture());
        assertEquals("myemail@gmail.com", returned.getEmail());
        assertEquals("myuser", returned.getLogin());
        assertEquals(ID, idCapture.getValue());
    }

    @Test
    void testFindByIdThrowingException() {
        // given
        when(repository.findById(ID)).thenReturn(Optional.empty());

        // when
        assertThrows(NotFoundException.class, () -> service.find(ID));
        // then
    }

    @Test
    void testSaveForNewUser() {
        // given
        User user = getNewUser();
        String encodedPassword = "encoded_pass";
        String oldPassword = user.getPassword();
        when(encoder.encode(oldPassword)).thenReturn(encodedPassword);
        ArgumentCaptor<User> userCapture = ArgumentCaptor.forClass(User.class);
        User saved = copy(user);
        saved.setPassword(encodedPassword);
        when(repository.save(userCapture.capture())).thenReturn(saved);

        // when
        User returned = service.save(user);

        // then
        verify(encoder).encode(oldPassword);
        verify(repository).save(userCapture.getValue());
        assertEquals(saved.getEmail(), returned.getEmail());
        assertEquals(saved.getPassword(), returned.getPassword());
        assertEquals(saved.getLogin(), returned.getLogin());
        assertEquals(saved.getFirstName(), returned.getFirstName());
        assertEquals(saved.getLastName(), returned.getLastName());
    }

    @Test
    void testSaveForExistingUser() {
        // given
        User user = getNewUser();
        user.setId(ID);
        User existingUser = getExistingUser();
        when(repository.findById(user.getId())).thenReturn(Optional.of(existingUser));

        // when
        User returned = service.save(user);

        // then
        verify(repository).findById(user.getId());
        verifyNoMoreInteractions(repository);
        verifyNoInteractions(encoder);
        assertNotNull(returned);
        assertEquals(user.getPassword(), returned.getPassword());
        assertEquals(user.getFirstName(), returned.getFirstName());
        assertEquals(user.getLastName(), returned.getLastName());
    }

    @Test
    void testForExistingUserChangingPassword() {
        // given
        User user = getNewUser();
        user.setId(ID);
        user.setPassword(PASSWORD_2);
        User existingUser = getExistingUser();
        when(repository.findById(user.getId())).thenReturn(Optional.of(existingUser));
        String encodedPassword = "encoded_password";
        when(encoder.encode(user.getPassword())).thenReturn(encodedPassword);

        // when
        User returned = service.save(user);

        // then
        verify(repository).findById(user.getId());
        verify(encoder).encode(user.getPassword());
        verifyNoMoreInteractions(repository);
        assertEquals(encodedPassword, returned.getPassword());
        assertEquals(user.getFirstName(), returned.getFirstName());
        assertEquals(user.getLastName(), returned.getLastName());
    }

    private static User getExistingUser() {
        User user = new User();
        user.setLogin(LOGIN);
        user.setPassword(PASSWORD);
        user.setEmail(EMAIL);
        user.setFirstName(FIRST_NAME_2);
        user.setLastName(LAST_NAME_2);

        return user;
    }

    private static User getNewUser() {
        User user = new User();
        user.setLogin(LOGIN);
        user.setPassword(PASSWORD);
        user.setEmail(EMAIL);
        user.setFirstName(FIRST_NAME);
        user.setLastName(LAST_NAME);

        return user;
    }

    private static User copy(User old) {
        User user = new User();
        user.setLogin(old.getLogin());
        user.setPassword(old.getPassword());
        user.setEmail(old.getEmail());
        user.setFirstName(old.getFirstName());
        user.setLastName(old.getLastName());

        return user;
    }
}
