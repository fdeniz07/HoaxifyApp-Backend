package com.hoaxify.ws.user;

import com.hoaxify.ws.email.EmailService;
import com.hoaxify.ws.user.exception.ActivationNotificationException;
import com.hoaxify.ws.user.exception.InvalidTokenException;
import com.hoaxify.ws.user.exception.NotFoundException;
import com.hoaxify.ws.user.exception.NotUniqueEmailException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.MailException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final EmailService emailService;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Transactional(rollbackOn = MailException.class)
    public void save(User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setActivationToken(UUID.randomUUID().toString());
            userRepository.saveAndFlush(user);
            emailService.sendActivationEmail(user.getEmail(), user.activationToken);
        } catch (DataIntegrityViolationException exception) {
            throw new NotUniqueEmailException();
        } catch (MailException exception) {
            throw new ActivationNotificationException();
        }
        /*
          if (user.getUsername() == null || user.getUsername().isEmpty()) {
            return ResponseMessage.builder()
                    .message(ErrorMessages.USERNAME_IS_INVALID)
                    .httpStatus(HttpStatus.NOT_ACCEPTABLE)
                    .build();
        }
        userRepository.save(user);
        return ResponseMessage.builder()
                .message("User is created")
                .httpStatus(HttpStatus.CREATED)
                .build();
         */
    }


    public void activateUser(String token) {

        User inDB = userRepository.findByActivationToken(token);
        if (inDB == null) {
            throw new InvalidTokenException();
        }
        inDB.setActive(true);
        inDB.setActivationToken(null);
        userRepository.save(inDB);
    }

    public Page<User> getUsers(Pageable page) {
        return userRepository.findAll(page);
    }

    public User getUser(long id) {
        return userRepository.findById(id).orElseThrow(() -> new NotFoundException(id));
    }


    public User findByEmail(String email) {
      return userRepository.findByEmail(email);
    }
}
