package com.springboot.minimartapi.security;
import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.proc.SecurityContext;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.security.web.SecurityFilterChain;

import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.interfaces.RSAPublicKey;
import java.util.Collection;
import java.util.UUID;

@RequiredArgsConstructor
@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfig {

    private final UserDetailServiceImpl userDetailService;
    private final PasswordEncoder passwordEncoder;
    @Bean
    DaoAuthenticationProvider daoAuthenticationProvider(){
        DaoAuthenticationProvider dao = new DaoAuthenticationProvider();
        dao.setUserDetailsService(userDetailService);
        dao.setPasswordEncoder(passwordEncoder);
        return dao;
    }
    @Bean
    JwtAuthenticationConverter jwtAuthenticationConverter (){
        return  new JwtAuthenticationConverter();
    }

    @Bean
    JwtAuthenticationProvider jwtAuthenticationProvider(@Qualifier("jwtRefreshDecoder") JwtDecoder jwtRefreshDecoder
            , JwtAuthenticationConverter jwtAuthenticationConverter) {
        JwtAuthenticationProvider provider = new JwtAuthenticationProvider(jwtRefreshDecoder);
        provider.setJwtAuthenticationConverter(jwtAuthenticationConverter);
        return provider;
    }
    @Bean
    SecurityFilterChain apiFilterChain(HttpSecurity httpSecurity, JwtAuthenticationConverter jwtAuthenticationConverter) throws Exception {
        httpSecurity.csrf(AbstractHttpConfigurer::disable);
        httpSecurity.sessionManagement(session-> session.sessionCreationPolicy(
                SessionCreationPolicy.STATELESS
        ));


        httpSecurity.authorizeHttpRequests(request ->
                request
                        .requestMatchers("/api/v1/auth/**", "/api/v1/products/**").permitAll()
                        .anyRequest().authenticated()

        )
        .oauth2ResourceServer(oauth2-> oauth2.jwt(jwt -> jwt.jwtAuthenticationConverter(jwtAuthenticationConverter)));
        return  httpSecurity.build();
    }
    @Primary
    @Bean
    KeyPair jwtKeyPair() {
        KeyPairGenerator keyPairGenerator = null;
        try {
            keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            return keyPairGenerator.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    @Primary
    @Bean
    RSAKey jwtRsa(KeyPair keyPair){
        return new RSAKey.Builder((RSAPublicKey) keyPair.getPublic())
                .privateKey(keyPair.getPrivate())
                .keyID(UUID.randomUUID().toString())
                .build();
    }

    @Primary
    @Bean
    JwtDecoder jwtDecoder(RSAKey rsaKey){
        try {
            return NimbusJwtDecoder.withPublicKey(rsaKey.toRSAPublicKey()).build();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }
    @Primary
    @Bean
    JWKSource<SecurityContext> jwkSource(RSAKey rsaKey){
        JWKSet jwkSet = new JWKSet(rsaKey);
        return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
    }
    @Primary
    @Bean
    JwtEncoder jwtEncoder (JWKSource<SecurityContext> jwkSource){
        return new NimbusJwtEncoder(jwkSource);
    }


    @Bean(name = "jwtRefreshKeyPair")
    KeyPair jwtRefreshKeyPair() {
        KeyPairGenerator keyPairGenerator = null;
        try {
            keyPairGenerator = KeyPairGenerator.getInstance("RSA");
            keyPairGenerator.initialize(2048);
            return keyPairGenerator.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }
    @Bean(name = "jwtRefreshRsa")
    RSAKey jwtRefreshRsa(@Qualifier("jwtRefreshKeyPair") KeyPair keyPair){
        return new RSAKey.Builder((RSAPublicKey) keyPair.getPublic())
                .privateKey(keyPair.getPrivate())
                .keyID(UUID.randomUUID().toString())
                .build();
    }
    @Bean(name = "jwtRefreshDecoder")
    JwtDecoder jwtRefreshDecoder(@Qualifier("jwtRefreshRsa") RSAKey rsaKey){
        try {
            return NimbusJwtDecoder.withPublicKey(rsaKey.toRSAPublicKey()).build();
        } catch (JOSEException e) {
            throw new RuntimeException(e);
        }
    }
    @Bean(name = "jwkRefreshSource")
    JWKSource<SecurityContext> jwkRefreshSource(@Qualifier("jwtRefreshRsa") RSAKey rsaKey){
        JWKSet jwkSet = new JWKSet(rsaKey);
        return (jwkSelector, securityContext) -> jwkSelector.select(jwkSet);
    }
    @Bean(name = "jwtRefreshEncoder")
    JwtEncoder jwtRefreshEncoder (@Qualifier("jwkRefreshSource") JWKSource<SecurityContext> jwkSource){
        return new NimbusJwtEncoder(jwkSource);
    }

}
