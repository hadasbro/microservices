package com.github.hadasbro.stock_service.config;

import com.github.hadasbro.stock_service.repository.UserRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import reactor.core.publisher.Mono;

/**
 * SecurityConfig
 */
@Configuration
class SecurityConfig {

    /**
     * springWebFilterChain
     *
     * @param http
     * @return
     * @throws Exception
     */
    @Bean
    SecurityWebFilterChain springWebFilterChain(ServerHttpSecurity http) throws Exception {

        return http
                .csrf()
                .disable()

//                .authorizeExchange()
//                .pathMatchers(HttpMethod.GET, "/products/**").permitAll()
//                .pathMatchers(HttpMethod.GET, "/orders/**").permitAll()
//                .pathMatchers(HttpMethod.GET, "/supply-orders/**").permitAll()
//
//                .pathMatchers(HttpMethod.POST, "/products/**").permitAll()
//                .pathMatchers(HttpMethod.POST, "/orders/**").permitAll()
//                .pathMatchers(HttpMethod.POST, "/supply-orders/**").permitAll()
//
//                .pathMatchers(HttpMethod.DELETE, "/products/**").hasRole("ADMIN")
//                .pathMatchers(HttpMethod.DELETE, "/orders/**").hasRole("ADMIN")
//
//                .pathMatchers("/products/**").authenticated()
//                .pathMatchers("/orders").authenticated()
//                .pathMatchers("/supply-orders").authenticated()
//
//                .anyExchange().permitAll()
//                .and()
                .build();
    }

    /**
     * currentUserMatchesPath
     *
     * @param authentication
     * @param context
     * @return
     */
    private Mono<AuthorizationDecision> currentUserMatchesPath(Mono<Authentication> authentication, AuthorizationContext context) {
        return authentication
                .map(
                        a -> context.getVariables()
                                .get("userUdt")
                                .equals(a.getName())
                )
                .map(AuthorizationDecision::new);
    }

    /**
     * userDetailsService
     *
     * @param users
     * @return
     */
    @Bean
    public ReactiveUserDetailsService userDetailsService(UserRepository users) {
        return (username) -> users.findByUsername(username)
                .map(u -> User.withUsername(u.getUsername())
//                        .password(u.getPassword())
//                        .authorities(u.getAuthorities())
//                        .accountExpired(u.isAccountNonExpired())
//                        .accountLocked(u.isAccountNonLocked())
                        .build()
                );
    }
}