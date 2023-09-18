package com.user.service;

import com.user.service.domain.User;
import com.user.service.dto.UserDto;
import com.user.service.maps.UserMapper;
import com.user.service.repositories.UserRepository;
import com.user.service.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

public class TestUserService {

    @Mock
    private UserRepository userRepository;

    private UserService userService;

    private final User sampleUser1 = new User(UUID.fromString("03a70a6b-c600-410f-a0bb-cd4682cc85be"),
            "trump.donald@gmail.com", "Donald Trump", LocalDate.now().minusYears(40),
            "Braga", "Portugal", "+351958412697", true);

    private final User sampleUser2 = new User(UUID.fromString("e54dba07-e880-4d6b-bb38-620eb06dd431"),
            "biden.joe@gmail.com", "Joe Biden", LocalDate.now().minusYears(24),
            "Braga", "Portugal", "+3519741852354", false);

    private final UserDto sampleUserDto = new UserDto(UUID.fromString("03a70a6b-c600-410f-a0bb-cd4682cc85be"),
            "trump.donald@gmail.com", "Donald Trump", LocalDate.now().minusYears(40),
            "Braga", "Portugal", "+351958412697", true);

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        userService = new UserService(userRepository);
    }

    @Test
    public void testAppointmentToDto(){

        UserDto result = UserMapper.INSTANCE.userToDto(sampleUser1);

        assertNotNull(result);
        assertEquals(result.getEmail(), sampleUser1.getEmail());
        assertEquals(result.getId(), sampleUser1.getId());
    }

    @Test
    public void testDtoToAppointment(){

        User result = UserMapper.INSTANCE.dtoToUser(sampleUserDto);

        assertNotNull(result);
        assertEquals(result.getEmail(), sampleUserDto.getEmail());
        assertEquals(result.getId(), sampleUserDto.getId());
    }

    @Test
    public void testGetAllProducts(){

        when(userRepository.findAll(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(sampleUser1, sampleUser2)));

        Page<UserDto> result = userService.getAllUsers(PageRequest.of(0, 10));

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
    }

    @Test
    public void testGetProductById(){

        when(userRepository.findById(UUID.fromString("a9c6998f-e346-4fee-a451-8290bef086fd")))
                .thenReturn(Optional.of(sampleUser1));

        UserDto result = userService.getUserById(UUID.fromString("a9c6998f-e346-4fee-a451-8290bef086fd"));

        assertNotNull(result);
        assertEquals(result.getEmail(), sampleUser1.getEmail());
    }

    @Test
    public void testCreateProduct(){

        when(userRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        UserDto result = userService.createUser(sampleUserDto);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(result.getEmail(), sampleUserDto.getEmail());
    }

    @Test
    public void testUpdateProduct(){

        when(userRepository.findById(sampleUserDto.getId())).thenReturn(Optional.of(sampleUser1));

        when(userRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        UserDto result = userService.updateUser(sampleUserDto.getId(), sampleUserDto);

        assertNotNull(result);
        assertEquals(result.getEmail(), sampleUserDto.getEmail());
    }

    @Test
    public void testDeleteProduct(){

        when(userRepository.findById(sampleUserDto.getId())).thenReturn(Optional.of(sampleUser1));

        assertDoesNotThrow(() -> userService.deleteUser(sampleUserDto.getId()));
    }
}
