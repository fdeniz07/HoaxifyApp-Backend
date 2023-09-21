package com.hoaxify.ws.user.validation;

import com.hoaxify.ws.user.User;
import com.hoaxify.ws.user.UserRepository;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

public class UniqueEmailValidator implements ConstraintValidator<UniqueEmail, String> { //Iki parametre aliyor; 1. Bizim tanimladigimi anatasyon sinifi
                                                                                        //                      2. Kullanilacak olan validasyon field'inin türü
    @Autowired
    UserRepository userRepository;

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {

        User inDB = userRepository.findByEmail(value);

        //Ilgili email DB de var mi kontrol+ü
        return inDB == null;
//        if (inDB != null){
//            return false;
//        }
//      return true;
    }
}
