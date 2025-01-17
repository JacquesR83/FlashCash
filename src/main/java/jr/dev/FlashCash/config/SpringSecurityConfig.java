package jr.dev.FlashCash.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SpringSecurityConfig {

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests((requests) -> requests
                        .requestMatchers("/signin", "/authentication", "/signup", "/static/**", "/css/**", "/images/**")
                        .permitAll()
                        .anyRequest()
                        .authenticated()
                )
                .formLogin((form) -> form
                        // customized login form
                        .loginPage("/signin")
//                        .loginProcessingUrl("/authentication") // To go through your postmapping /authentication with spring security own means
                        // username is default field and is changed here (=> checked field in the HTML form login page)
                        .permitAll().usernameParameter("email")
                        // when successful, goes to "/" URL, => always
                        .defaultSuccessUrl("/home", true)
                        // Add an error code to the URL when login fails
                        .failureUrl("/signin?authError=true")
                )
                // logout access
                .logout((logout) -> logout
                        .logoutUrl("/logout")
                        .permitAll());

        return http.build();
    }
}
