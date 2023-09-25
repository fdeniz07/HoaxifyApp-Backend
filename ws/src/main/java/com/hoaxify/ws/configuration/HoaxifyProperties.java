package com.hoaxify.ws.configuration;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Getter
@Setter
@ConfigurationProperties(prefix = "hoaxify") //hoaxify ile baslayan degerleri burada set edecegiz
@Configuration
public class HoaxifyProperties {

    private Email email;
    private Client client;

    public static record Email(
            String username,
            String password,
            String host,
            int port,
            String from
    ) {    }

    public static record Client(
            String host
    ){}
}
