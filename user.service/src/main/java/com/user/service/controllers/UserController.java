package com.user.service.controllers;

import com.user.service.dto.UserDto;
import com.user.service.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Tag(name = "User", description = "Manage System Users")
@RequestMapping("/user")
@RestController
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    @Operation(description = "Get all Users")
    @ResponseStatus(HttpStatus.OK)
    public Page<UserDto> getAllUsers(Pageable pageable,
                                     @Parameter(description = "Filter by availability.")
                                     @RequestParam(value = "Enable", required = false) Boolean isEnable,
                                     @Parameter(description = "Filter by Email or FullName.")
                                     @RequestParam(value = "Search", required = false) String search){

        return this.userService.getAllUsers(pageable, isEnable, search);
    }

    @GetMapping("/{id}")
    @Operation(description = "Get User by id")
    @ResponseStatus(HttpStatus.OK)
    public UserDto getUserById(@PathVariable UUID id){

        return this.userService.getUserById(id);
    }

    @PostMapping("/")
    @Operation(description = "Create User")
    @ResponseStatus(HttpStatus.OK)
    public UserDto createUser(@RequestBody UserDto userDto){

        return this.userService.createUser(userDto);
    }

    @PatchMapping("/{id}")
    @Operation(description = "Update User by id")
    @ResponseStatus(HttpStatus.OK)
    public UserDto updateUser(@PathVariable UUID id, @RequestBody UserDto userDto){

        return this.userService.updateUser(id, userDto);
    }

    @DeleteMapping("/{id}")
    @Operation(description = "Delete User by id")
    @ResponseStatus(HttpStatus.OK)
    public void deleteUser(@PathVariable UUID id){

        this.userService.deleteUser(id);
    }
}
