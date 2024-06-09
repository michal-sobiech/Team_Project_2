package org.pzsp2.proman.security;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;


@Configuration
@ConfigurationProperties(prefix = "rsa")
public class RSAKeyProperties {
    public @Getter @Setter RSAPublicKey publicKey;
    public @Getter @Setter RSAPrivateKey privateKey;
}
