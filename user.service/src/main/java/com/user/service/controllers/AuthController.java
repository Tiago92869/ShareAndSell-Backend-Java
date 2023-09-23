package com.user.service.controllers;

import com.user.service.services.UserService;
import com.user.service.utils.AuthRequest;
import com.user.service.utils.JwtTokenUtil;
import com.user.service.utils.LoginResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.security.SecureRandom;
import java.util.Base64;

@Tag(name = "Authentication", description = "Authenticate Users")
@RequestMapping("/auth")
@RestController
public class AuthController {

   // @Autowired
   // private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @PostMapping("/login")
    @Operation(description = "Login User")
    @ResponseStatus(HttpStatus.OK)
    public LoginResponse createAuthenticationToken(@RequestBody AuthRequest authenticationRequest) {
        // Authenticate the user
        //Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
       //         authenticationRequest.getEmail(), authenticationRequest.getPassword()));

        // Generate JWT if authentication is successful
        //UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        final String jwtToken = jwtTokenUtil.generateToken(authenticationRequest);

        // Generate a refresh token (you can customize this part)
        final String refreshToken = generateRefreshToken();

        // Return the JWT and refresh token in the response
        return new LoginResponse(jwtToken, refreshToken);
    }

    private String generateRefreshToken() {
        // Generate a random byte array to create the refresh token
        byte[] randomBytes = new byte[32]; // Adjust the length as needed

        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(randomBytes);

        // Encode the random byte array as a Base64 string
        String refreshToken = Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes);

        return refreshToken;
    }
}
