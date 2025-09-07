package co.com.crediya.api.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "routes.paths")
public class Path {
    private String users;
    private String existUserByEmail;
    private String getUserByEmail;
    private String login;
    
}

