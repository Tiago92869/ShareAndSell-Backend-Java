package com.user.service.services;

import com.user.service.domain.User;
import com.user.service.dto.UserDto;
import com.user.service.exceptions.EntityNotFoundException;
import com.user.service.maps.UserMapper;
import com.user.service.rabbit.ProducerService;
import com.user.service.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public Page<UserDto> getAllUsers(Pageable pageable, Boolean isEnable) {

        if (isEnable != null) {

            return this.userRepository.findByIsEnable(pageable, isEnable).map(UserMapper.INSTANCE::userToDto);
        }

        return this.userRepository.findAll(pageable).map(UserMapper.INSTANCE::userToDto);
    }

    public UserDto getUserById(UUID id) {

        Optional<User> optionalUser = this.userRepository.findById(id);

        if(optionalUser.isEmpty()){
            throw new EntityNotFoundException("A user with that id does not exist");
        }

        return UserMapper.INSTANCE.userToDto(optionalUser.get());
    }

    public UserDto createUser(UserDto userDto) {

        userDto.setId(UUID.randomUUID());

        User user = UserMapper.INSTANCE.dtoToUser(userDto);

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

        return UserMapper.INSTANCE.userToDto(this.userRepository.save(user));
    }

    public void deleteUser(UUID id) {

        Optional<User> optionalUser = this.userRepository.findById(id);

        if(optionalUser.isEmpty()){
            throw new EntityNotFoundException("A user with that id does not exist");
        }

        this.producerService.deleteAppointmentByUserId(String.valueOf(id));
        this.userRepository.deleteById(id);
    }

    public void removeShopIdFromFavorites(UUID shopId){

        List<User> userList = this.userRepository.findByFavoritesContaining(shopId);

        userList.forEach(user -> user.setFavorites(
                user.getFavorites()
                        .stream()
                        .filter(shopInList -> !shopInList.equals(shopId))
                        .collect(Collectors.toList())
        ));

        this.userRepository.saveAll(userList);
    }
}
