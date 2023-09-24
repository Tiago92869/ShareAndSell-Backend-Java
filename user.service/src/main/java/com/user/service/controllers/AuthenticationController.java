package com.user.service.controllers;

import com.user.service.dto.Authentication;
import com.user.service.dto.Token;
import com.user.service.services.AuthenticationService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Authentication", description = "Authenticate Users")
@RequestMapping("/auth")
@RestController
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @Autowired
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }

    @PostMapping("/login")
    @Operation(summary = "Login user")
    @ResponseStatus(HttpStatus.OK)
    public Token loginUser(@RequestBody Authentication authentication){

        return this.authenticationService.loginUser(authentication);
    }
}
