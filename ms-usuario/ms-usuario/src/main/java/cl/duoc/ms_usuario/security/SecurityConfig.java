package cl.duoc.ms_usuario.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            // 1. Deshabilitar CSRF porque las APIs REST con JWT son "stateless"
            .csrf(csrf -> csrf.disable()) 
            
            // 2. Configurar qué rutas son libres y cuáles requieren token
            .authorizeHttpRequests(auth -> auth
                .requestMatchers("/api/v1/usuario/login").permitAll() 
            );

        return http.build();
    }
}

