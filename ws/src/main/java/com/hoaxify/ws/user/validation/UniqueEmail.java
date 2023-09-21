package com.hoaxify.ws.user.validation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//Bu yapi bize kendi spesifik constraintler olusturmamizi sagliyor
@Constraint(validatedBy = UniqueEmailValidator.class) //Constraint'leri yazacagimiz sinif
@Target({ElementType.FIELD}) //Field bazinda annaotation oldugunu belirtiyoruz
@Retention(RetentionPolicy.RUNTIME) //Calisma zamani boyunca aktif olmasini istiyoruz
public @interface UniqueEmail { //@interface ile annatotion tanimlayabiliyoruz

    //Buradaki 3 field default olarak yaziliyor

    String message() default "This E-mail already exist";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    //!!! Daha Detayli bilgi icin : https://jakarta.ee/specifications/bean-validation/3.0/jakarta-bean-validation-spec-3.0.html

}
