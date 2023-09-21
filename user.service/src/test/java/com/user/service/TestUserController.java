package com.user.service;

import com.user.service.controllers.UserController;
import com.user.service.dto.UserDto;
import com.user.service.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@ExtendWith(SpringExtension.class)
@WebMvcTest(UserController.class)
public class TestUserController {


    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    private final List<UUID> shopIdList= List.of(UUID.fromString("77032de3-1a76-4d49-a49c-adf64ca7e05f"),
            UUID.fromString("fbbcc1a7-b6ef-453f-890d-f459bd0da93a"));

    private final UserDto sampleUserDto1 = new UserDto(UUID.fromString("03a70a6b-c600-410f-a0bb-cd4682cc85be"),
            "trump.donald@gmail.com", "Donald Trump", LocalDate.now().minusYears(40),
            "Braga", "Portugal", "+351958412697", true, shopIdList);

    private final UserDto sampleUserDto2 = new UserDto(UUID.fromString("e54dba07-e880-4d6b-bb38-620eb06dd431"),
            "biden.joe@gmail.com", "Joe Biden", LocalDate.now().minusYears(24),
            "Braga", "Portugal", "+3519741852354", false, shopIdList);

    private final String sampleUserJson = "{\"email\": \"trump.donald@gmail.com\"," +
            "\"fullName\": \"Donald Trump\"," +
            "\"birthdate\": \"1983-09-18\"," +
            "\"city\": \"Braga\"," +
            "\"country\": \"Portugal\"," +
            "\"phoneNumber\": \"351958412697\"," +
            "\"isEnable\": true," +
            "\"favorites\": [" +
            "\"77032de3-1a76-4d49-a49c-adf64ca7e05f\"]}";


    @Test
    public void testGetAllUsers() throws Exception {

        when(userService.getAllUsers(any(Pageable.class), eq(null), eq(null)))
                .thenReturn(new PageImpl<>(List.of(sampleUserDto1, sampleUserDto2)));

        mockMvc.perform(MockMvcRequestBuilders.get("/user/"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", hasSize(2)));
    }

    @Test
    public void testGetAllUsersTrue() throws Exception {

        when(userService.getAllUsers(any(Pageable.class), any(Boolean.class),eq(null)))
                .thenReturn(new PageImpl<>(List.of(sampleUserDto1)));

        mockMvc.perform(MockMvcRequestBuilders.get("/user/")
                        .param("Enable", String.valueOf(Boolean.TRUE)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", hasSize(1)));
    }

    @Test
    public void testGetAllUsersSearch() throws Exception {

        when(userService.getAllUsers(any(Pageable.class), eq(null), any(String.class)))
                .thenReturn(new PageImpl<>(List.of(sampleUserDto1)));

        mockMvc.perform(MockMvcRequestBuilders.get("/user/")
                        .param("Search", "trump"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", hasSize(1)));
    }

    @Test
    public void testGetAllUsersTrueSearch() throws Exception {

        when(userService.getAllUsers(any(Pageable.class), any(Boolean.class), any(String.class)))
                .thenReturn(new PageImpl<>(List.of(sampleUserDto1)));

        mockMvc.perform(MockMvcRequestBuilders.get("/user/")
                        .param("Enable", String.valueOf(Boolean.TRUE))
                        .param("Search", "trump"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").isArray())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content", hasSize(1)));
    }

    @Test
    public void testGetUserById() throws Exception {

        when(userService.getUserById(any(UUID.class))).thenReturn(sampleUserDto1);

        mockMvc.perform(MockMvcRequestBuilders.get("/user/{id}", sampleUserDto1.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(sampleUserDto1.getEmail()));
    }

    @Test
    public void testCreateUser() throws Exception {

        when(userService.createUser(any(UserDto.class))).thenReturn(sampleUserDto1);

        mockMvc.perform(MockMvcRequestBuilders.post("/user/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(sampleUserJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(sampleUserDto1.getEmail()));
    }

    @Test
    public void testUpdateUser() throws Exception {

        when(userService.updateUser(any(UUID.class), any(UserDto.class))).thenReturn(sampleUserDto1);

        mockMvc.perform(MockMvcRequestBuilders.patch("/user/{id}", sampleUserDto1.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(sampleUserJson))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.email").value(sampleUserDto1.getEmail()));
    }

    @Test
    public void testDeleteUser() throws Exception {

        doNothing().when(userService).deleteUser(any(UUID.class));

        mockMvc.perform(MockMvcRequestBuilders.delete("/user/{id}", sampleUserDto1.getId()))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}