package com.hoaxify.ws.user;

import com.hoaxify.ws.shared.GenericMessage;
import com.hoaxify.ws.shared.ResponseMessage;
import com.hoaxify.ws.shared.utils.ErrorMessages;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public void save(User user) {

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        userRepository.save(user);
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
