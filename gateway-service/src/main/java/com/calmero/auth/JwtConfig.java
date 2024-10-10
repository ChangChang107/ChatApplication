package com.calmero.auth;

import com.calmero.constant.HttpConstant;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "jwt")
public class JwtConfig {

    private String secretKey;
    private String tokenPrefix;
    private int tokenExpirationForVerification;
    private int tokenExpirationForLogin;

    public String getAuthorizationHeader() {
        return HttpConstant.AUTHORIZATION;
    }

}
