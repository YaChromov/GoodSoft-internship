package org.example.t5s.config;

import jakarta.servlet.DispatcherType;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)

                .authorizeHttpRequests(auth -> auth
                        .dispatcherTypeMatchers(DispatcherType.FORWARD, DispatcherType.ERROR).permitAll()
                        .requestMatchers("/login.jhtml", "/css/**", "/js/**", "/error").permitAll()
                        .requestMatchers("/welcome.jhtml", "/loginedit.jhtml").authenticated()
                        .anyRequest().hasRole("ADMIN")
                )

                .formLogin(form -> form
                        .loginPage("/login.jhtml")
                        .loginProcessingUrl("/login.jhtml")
                        .usernameParameter("login")
                        .passwordParameter("password")
                        .defaultSuccessUrl("/welcome.jhtml", true)
                        .failureUrl("/login.jhtml?error=true")
                        .permitAll()
                )

                .logout(logout -> logout
                        .logoutUrl("/logout.jhtml")
                        .logoutSuccessUrl("/login.jhtml?logout=true")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}