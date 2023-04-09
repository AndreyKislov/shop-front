package ua.kislov.shop_front.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import ua.kislov.shop_front.security.exeption_handler.CustomAuthenticationFailureHandler;

import java.util.Collections;

@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfig {

    private final CustomAuthenticationFailureHandler handler;
    private final AuthProvider authProvider;

    @Autowired
    public SecurityConfig(CustomAuthenticationFailureHandler handler, AuthProvider authProvider) {
        this.handler = handler;
        this.authProvider = authProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/shop/deleteP")
                        .hasRole("ADMIN")
                        .requestMatchers("/auth/login", "error", "/auth/registration")
                        .permitAll()
                        .anyRequest().hasAnyRole("USER", "ADMIN")
                ).formLogin(form -> form
                        .loginPage("/auth/login")
                        .loginProcessingUrl("/process_login")
                        .defaultSuccessUrl("/shop/checkPage", true)
                        .failureHandler(handler)
                ).logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/auth/login")
                ).build();
    }
    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(Collections.singletonList(authProvider));
    }
}
