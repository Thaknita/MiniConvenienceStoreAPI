package com.springboot.minimartapi.auth;

import com.springboot.minimartapi.auth.dto.*;
import com.springboot.minimartapi.user.User;

import com.springboot.minimartapi.user.UserService;
import com.springboot.minimartapi.util.RandomNumber;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.server.resource.authentication.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationProvider;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthServiceImpl implements AuthService{

    private final UserService userService;
    private final DaoAuthenticationProvider daoAuthenticationProvider;
    private final UserRegistrationMapper userRegistrationMapper;
    private final AuthRepo authRepo;
    private final JwtAuthenticationProvider jwtAuthenticationProvider;
    private final JwtEncoder jwtEncoder;
    private final JavaMailSender javaMailSender;
    @Value("${spring.mail.username}")
    private String adminMail;
    private JwtEncoder jwtRefreshTokenEncoder;
    private String creatAccessToken(Authentication authentication){
        Instant now = Instant.now();
        String scope = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .id(authentication.getName())
                .audience(List.of("Mobile", "Web"))
                .issuedAt(now)
                .expiresAt(now.plus(1, ChronoUnit.MINUTES))
                .issuer(authentication.getName())
                .subject("access token")
                .claim("scope", scope)
                .build();
        return jwtEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();
    }

    private String createRefreshToken(Authentication authentication){
        Instant now = Instant.now();
        JwtClaimsSet jwtClaimsSet = JwtClaimsSet.builder()
                .id(authentication.getName())
                .audience(List.of("Mobile", "Web"))
                .issuedAt(now)
                .expiresAt(now.plus(30, ChronoUnit.DAYS))
                .issuer(authentication.getName())
                .subject("access token")
                .build();
        return jwtRefreshTokenEncoder.encode(JwtEncoderParameters.from(jwtClaimsSet)).getTokenValue();
    }

    @Autowired
    @Qualifier("jwtRefreshEncoder")
    public void setJwtRefreshTokenEncoder(JwtEncoder jwtRefreshTokenEncoder){
        this.jwtRefreshTokenEncoder = jwtRefreshTokenEncoder;
    }

    @Override
    public AuthDto refresh(RefreshTokenDto refreshTokenDto) {

        Authentication auth = new BearerTokenAuthenticationToken(refreshTokenDto.refreshToken());

        auth = jwtAuthenticationProvider.authenticate(auth);

        return AuthDto.builder()
                .tokenType("Bearer")
                .accessToken(this.creatAccessToken(auth))
                .refreshToken(this.createRefreshToken(auth))
                .build();
    }

    @Override
    public AuthDto login(LoginDto loginDto) {

        Authentication auth = new UsernamePasswordAuthenticationToken(
                loginDto.emailAddress(),
                loginDto.password()
        );
        auth = daoAuthenticationProvider.authenticate(auth);
        log.info("Auth: {}", auth);
        log.info("Auth:{}", auth.getName());
        log.info("Auth: {}", auth.getAuthorities());
        return AuthDto.builder()
                .tokenType("Bearer")
                .accessToken(this.creatAccessToken(auth))
                .refreshToken(this.createRefreshToken(auth))
                .build();
    }
    @Transactional
    @Override
    public Map<String, Object> registerUser(UserRegistrationDto userRegistrationDto) throws MessagingException {
        if (!userRegistrationDto.password().equals(userRegistrationDto.confirmPassword())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Password doesn't match!");
        }

        userService.createUser(userRegistrationMapper.fromUserRegistrationDto(userRegistrationDto));

        String verifyDigit = RandomNumber.getSixDigit();
        authRepo.updateVerifiedCode(userRegistrationDto.emailAddress(), verifyDigit);

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage);
        mimeMessageHelper.setSubject("Account Verification");
        mimeMessageHelper.setText(verifyDigit);
        mimeMessageHelper.setTo(userRegistrationDto.emailAddress());
        mimeMessageHelper.setFrom(adminMail);
        //Send Message
        javaMailSender.send(mimeMessage);
        
        return Map.of("Message","Please check your email to  verify your account ! ");
    }
    @Override
    public Map<String, Object> verifyUser(VerifyUserDto verifyUserDto) {

        User user = authRepo.findByEmailAddressAndVerifyCode(verifyUserDto.emailAddress(), verifyUserDto.verifyCode())
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "incorrect verify code")
                );

        user.setIsVerified(true);
        user.setVerifyCode(null);

        authRepo.save(user);

        return Map.of("Message", "verified successfully");
    }
}
