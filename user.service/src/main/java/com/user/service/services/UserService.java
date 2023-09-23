package com.user.service.services;

import com.user.service.domain.User;
import com.user.service.dto.UserDto;
import com.user.service.exceptions.BadRequestException;
import com.user.service.exceptions.EntityNotFoundException;
import com.user.service.maps.UserMapper;
import com.user.service.rabbit.ProducerService;
import com.user.service.repositories.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    private final UserRepository userRepository;

    private final ProducerService producerService;

    @Autowired
    public UserService(UserRepository userRepository, ProducerService producerService) {
        this.userRepository = userRepository;
        this.producerService = producerService;
    }

    public Page<UserDto> getAllUsers(Pageable pageable, Boolean isEnable, String search) {

        this.producerService.sendMessageLogService("Get All Users", "45fbf752-1e87-4086-93d3-44e637c26a96");
        if (isEnable != null && search == null) {

            return this.userRepository.findByIsEnable(pageable, isEnable).map(UserMapper.INSTANCE::userToDto);
        }

        if(isEnable == null && search != null){

            return this.userRepository.findByEmailContainingIgnoreCaseOrFullNameContainingIgnoreCase(
                    pageable, search, search).map(UserMapper.INSTANCE::userToDto);
        }

        if(isEnable != null && search != null){

            return this.userRepository.findByEmailContainingIgnoreCaseAndIsEnableOrFullNameContainingIgnoreCaseAndIsEnable(
                    pageable, search, isEnable, search, isEnable).map(UserMapper.INSTANCE::userToDto);
        }

        return this.userRepository.findAll(pageable).map(UserMapper.INSTANCE::userToDto);
    }

    public UserDto getUserById(UUID id) {

        Optional<User> optionalUser = this.userRepository.findById(id);

        if(optionalUser.isEmpty()){
            throw new EntityNotFoundException("A user with that id does not exist");
        }

        this.producerService.sendMessageLogService("Get User with Id " + id, "45fbf752-1e87-4086-93d3-44e637c26a96");
        return UserMapper.INSTANCE.userToDto(optionalUser.get());
    }

    public UserDto createUser(UserDto userDto) {

        if(userDto.getEmail() == null || userDto.getPassword() == null){

            throw new BadRequestException("Email and Password are needed to complete the creation of user");
        }

        userDto.setId(UUID.randomUUID());

        //hash the password
        String hashPassword = new BCryptPasswordEncoder().encode(userDto.getPassword());
        userDto.setPassword(hashPassword);

        User user = UserMapper.INSTANCE.dtoToUser(userDto);

        this.producerService.sendMessageLogService("Create User with Id " + user.getId(), "45fbf752-1e87-4086-93d3-44e637c26a96");
        return UserMapper.INSTANCE.userToDto(this.userRepository.save(user));
    }

    public UserDto updateUser(UUID id, UserDto userDto) {

        Optional<User> optionalUser = this.userRepository.findById(id);

        if(optionalUser.isEmpty()){
            throw new EntityNotFoundException("A user with that id does not exist");
        }

        User user = optionalUser.get();

        if(userDto.getIsEnable() != null){

            user.setIsEnable(userDto.getIsEnable());
        }

        if(userDto.getCity() != null){

            user.setCity(userDto.getCity());
        }

        if(userDto.getEmail() != null){

            user.setEmail(userDto.getEmail());
        }

        if(userDto.getBirthdate() != null){

            user.setBirthdate(userDto.getBirthdate());
        }

        if(userDto.getCountry() != null){

            user.setCountry(userDto.getCountry());
        }

        if(userDto.getFullName() != null){

            user.setFullName(userDto.getFullName());
        }

        if(userDto.getPhoneNumber() != null){

            user.setPhoneNumber(userDto.getPhoneNumber());
        }

        if(userDto.getFavorites() != null){

            user.setFavorites(userDto.getFavorites());
        }

        if(userDto.getPassword() != null){

            user.setPassword(new BCryptPasswordEncoder().encode(userDto.getPassword()));
        }

        this.producerService.sendMessageLogService("Update User with Id " + user.getId(), "45fbf752-1e87-4086-93d3-44e637c26a96");
        return UserMapper.INSTANCE.userToDto(this.userRepository.save(user));
    }

    public void deleteUser(UUID id) {

        Optional<User> optionalUser = this.userRepository.findById(id);

        if(optionalUser.isEmpty()){
            throw new EntityNotFoundException("A user with that id does not exist");
        }

        this.producerService.deleteAppointmentByUserId(String.valueOf(id));
        this.producerService.sendMessageLogService("Delete User with Id " + id, "45fbf752-1e87-4086-93d3-44e637c26a96");
        this.userRepository.deleteById(id);
    }

    @Transactional
    public void removeShopIdFromFavorites(UUID shopId){

        List<User> userList = this.userRepository.findByFavoritesContaining(shopId);

        userList.forEach(user -> user.setFavorites(
                user.getFavorites()
                        .stream()
                        .filter(shopInList -> !shopInList.equals(shopId))
                        .collect(Collectors.toList())
        ));

        this.producerService.sendMessageLogService("Remove ShopId from favorites with the shopId " + shopId, "45fbf752-1e87-4086-93d3-44e637c26a96");
        this.userRepository.saveAll(userList);
    }
}
