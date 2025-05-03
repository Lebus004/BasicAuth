package ch.fhnw.securitytest.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Bean
    public UserDetailsService users() {
        // Erstellen von zwei Benutzern mit unterschiedlichen Rollen und Hinzufügen zum In-Memory-User-Store
        return new InMemoryUserDetailsManager(
            User.withUsername("myuser")
                .password("{noop}password") // {noop} bedeutet, dass das Passwort nicht verschlüsselt ist
                .authorities("READ", "ROLE_USER")
                .build(),
            User.withUsername("myadmin")
                .password("{noop}password")
                .authorities("READ", "ROLE_ADMIN")
                .build()
        );
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable()) // CSRF deaktivieren, da wir eine REST-API entwickeln
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/securitytest/admin").hasRole("ADMIN") // Zugriff nur für ADMIN
                        .requestMatchers("/securitytest/user").hasRole("USER")  // Zugriff nur für USER
                        .requestMatchers("/securitytest/**").permitAll()        // Zugriff für alle auf andere Endpunkte
                )
                .formLogin(withDefaults()) // Standard-Login-Formular von Spring Security verwenden
                .httpBasic(withDefaults()) // HTTP Basic Authentication aktivieren
                .build();
    }
}