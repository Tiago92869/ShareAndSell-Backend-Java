package com.user.service.services;

import com.user.service.domain.User;
import com.user.service.dto.Authentication;
import com.user.service.dto.Token;
import com.user.service.exceptions.BadRequestException;
import com.user.service.exceptions.EntityNotFoundException;
import com.user.service.repositories.UserRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@Service
public class AuthenticationService {

    @Value("${jwt.secret}")
    private String SECRET_KEY;

    @Value("${jwt.expiration.access}")
    private long ACCESS_TIME;

    @Value("${jwt.expiration.refresh}")
    private long REFRESH_TIME;

    private final UserRepository userRepository;

    private final UserService userService;

    @Autowired
    public AuthenticationService(UserRepository userRepository, UserService userService) {
        this.userRepository = userRepository;
        this.userService = userService;
    }

    public String generateAccessToken(String email) {
        return generateToken(email, ACCESS_TIME);
    }

    public String generateRefreshToken(String email) {
        return generateToken(email, REFRESH_TIME);
    }

    private String generateToken(String email, long expiration) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + expiration);

        Map<String, Object> claims = new HashMap<>();
        claims.put("email", email);

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, SECRET_KEY)
                .compact();
    }

    public Token loginUser(Authentication authentication) {

        Optional<User> optionalUser = this.userRepository.findByEmail(authentication.getEmail());

        if(optionalUser.isEmpty()){
            throw new EntityNotFoundException("There is no user with that email");
        }

        if(!this.userService.checkPassword(optionalUser.get(), authentication.getPassword())){
            throw new BadRequestException("The password does not match");
        }

        return new Token(generateAccessToken(authentication.getEmail()), generateRefreshToken(authentication.getEmail()));
    }
}
