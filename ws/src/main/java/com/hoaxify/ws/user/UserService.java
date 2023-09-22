package com.hoaxify.ws.user;

import com.hoaxify.ws.user.exception.ActivationNotificationException;
import com.hoaxify.ws.user.exception.NotUniqueEmailException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


import java.util.Properties;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @Transactional(rollbackOn = MailException.class)
    public void save(User user) {
        try {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            user.setActivationToken(UUID.randomUUID().toString());
            userRepository.saveAndFlush(user);
            sendActivationEmail(user);
        } catch (DataIntegrityViolationException exception) {
            throw new NotUniqueEmailException();
        }catch (MailException exception){
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

    private void sendActivationEmail(User user) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("noreply@my-app.com");
        message.setTo(user.getEmail());
        message.setSubject("Activation");
        message.setText("http://localhost:5173/activation"+user.getActivationToken());
        getJavaMailSender().send(message);
    }

    public JavaMailSender getJavaMailSender(){
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost("smtp.ethereal.email");
        mailSender.setPort(587);
        mailSender.setUsername("anika88@ethereal.email");
        mailSender.setPassword("bQWRs7Dxp77C19vR5A-");

        Properties properties = mailSender.getJavaMailProperties();
        properties.put("mail.smtp.starttls.enable",true);
        return mailSender;
    }
}
