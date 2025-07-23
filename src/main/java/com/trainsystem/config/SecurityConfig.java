package com.trainsystem.config;

import com.trainsystem.services.AuthService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder; // Use BCrypt in production
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
public class SecurityConfig {

    private final AuthService authService;

    public SecurityConfig(AuthService authService) {
        this.authService = authService;
    }

    /**
     * Builder Pattern: Configures HttpSecurity step-by-step using chained builders.
     */
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        // Step 1: Authorization configuration
        http.authorizeHttpRequests(authz -> authz
                .requestMatchers("/css/**", "/js/**", "/signup", "/h2-console/**").permitAll()
                .anyRequest().authenticated()
        )

        // Step 2: Login configuration
        .formLogin(form -> form
                .loginPage("/login")
                .defaultSuccessUrl("/dashboard", true)
                .failureUrl("/login?error=true")
                .permitAll()
        )

        // Step 3: Logout configuration
        .logout(logout -> logout
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login?logout")
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
        )

        // Step 4: Allow H2 console by disabling frame options (for dev only)
        .headers(headers -> headers
                .frameOptions(frame -> frame.disable())
        )

        // Step 5: Disable CSRF for H2 console
        .csrf(csrf -> csrf
                .ignoringRequestMatchers("/h2-console/**")
        );

        // Final Step: Build the configured security filter chain
        return http.build();
    }

    /**
     * Factory Pattern: Creates and configures DaoAuthenticationProvider
     */
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();

        // Strategy Pattern: Delegates to AuthService for user details retrieval
        authProvider.setUserDetailsService(authService);

        // Strategy Pattern: Chooses password encoding strategy (NoOp for now)
        authProvider.setPasswordEncoder(passwordEncoder());

        return authProvider;
    }

    /**
     * Strategy Pattern: Defines the password encoding strategy.
     * Replace with BCryptPasswordEncoder for production.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance(); // Plain text (TEMP)
        // return new BCryptPasswordEncoder(); // Secure option
    }

    /**
     * Factory Method: Provides AuthenticationManager from configuration
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
