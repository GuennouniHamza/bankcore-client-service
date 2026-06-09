package ma.bankcore.client_service.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;
import org.springframework.security.web.SecurityFilterChain;

@Configuration //classe contient des configurations charge le au demarage
@EnableWebSecurity
@EnableMethodSecurity//Active les annotations de sécurité sur les méthodes 

public class SecurityConfig {
	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
	    http
	        .csrf(csrf -> csrf.disable())
	        .sessionManagement(session ->
	            session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
	        .authorizeHttpRequests(auth -> auth
	            .requestMatchers("/swagger-ui/**",
	                             "/api-docs/**").permitAll()
	            .anyRequest().authenticated()
	        )
	        .oauth2ResourceServer(oauth2 ->
	            oauth2.jwt(jwt ->
	                jwt.jwtAuthenticationConverter(
	                    jwtAuthenticationConverter())
	            )
	        );
	    return http.build();
	}
	@Bean
	public JwtAuthenticationConverter jwtAuthenticationConverter() {
	    JwtGrantedAuthoritiesConverter converter = 
	        new JwtGrantedAuthoritiesConverter();
	    converter.setAuthoritiesClaimName("realm_access.roles");//ou chercher les roles dans le JWT 
	    converter.setAuthorityPrefix("ROLE_");

	    JwtAuthenticationConverter jwtConverter = 
	        new JwtAuthenticationConverter();
	    jwtConverter.setJwtGrantedAuthoritiesConverter(converter);
	    return jwtConverter;
	}
}
