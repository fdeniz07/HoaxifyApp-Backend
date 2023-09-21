package com.hoaxify.ws.user;

import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void save(User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepository.save(user);
        } catch (DataIntegrityViolationException exception) {
            throw new NotUniqueEmailException();
        }
//        if (user.getUsername() == null || user.getUsername().isEmpty()) {
//            return ResponseMessage.builder()
//                    .message(ErrorMessages.USERNAME_IS_INVALID)
//                    .httpStatus(HttpStatus.NOT_ACCEPTABLE)
//                    .build();
//        }
//        userRepository.save(user);
//        return ResponseMessage.builder()
//                .message("User is created")
//                .httpStatus(HttpStatus.CREATED)
//                .build();
    }
}
