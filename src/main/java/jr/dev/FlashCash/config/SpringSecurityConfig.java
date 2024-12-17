package jr.dev.FlashCash.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity

public class SpringSecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/signin", "/signup", "/index.css", "/images/**")
                        .permitAll()
                        .anyRequest()
                        .authenticated()
                )
                .formLogin((form) -> form
                        // customized login form
                        .loginPage("signin")
                        // username is default field and is changed here (=> checked field in the HTML form login page)
                        .permitAll().usernameParameter("email")
                        // when successful, goes to "/" URL, => always
                        .defaultSuccessUrl("/", true)
                )
                // logout access
                .logout((logout) -> logout.permitAll());
        return http.build();
    }
}
