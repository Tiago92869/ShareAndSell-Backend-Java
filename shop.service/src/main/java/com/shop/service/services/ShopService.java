package com.shop.service.services;
import com.shop.service.domain.Shop;
import com.shop.service.domain.WeekDays;
import com.shop.service.dto.ShopDto;
import com.shop.service.exceptions.BadRequestException;
import com.shop.service.exceptions.EntityNotFoundException;
import com.shop.service.maps.ShopMapper;
import com.shop.service.rabbit.ProducerService;
import com.shop.service.repositories.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ShopService {

    private final List<String> weekDaysList = Arrays.asList("MONDAY", "TUESDAY", "WEDNESDAY", "THURSDAY", "FRIDAY", "SATURDAY", "SUNDAY");

    private final ShopRepository shopRepository;

    private final ProducerService producerService;

    @Autowired
    public ShopService(ShopRepository shopRepository, ProducerService producerService) {
        this.shopRepository = shopRepository;
        this.producerService = producerService;
    }

    public Page<ShopDto> getAllShops(Pageable pageable, List<String> weekDays, Boolean isEnable) {

        List<WeekDays> enumWeekDays = new ArrayList<>();

        if(weekDays != null){

            //check if there is a invalid weekDay
            if(!new HashSet<>(this.weekDaysList).containsAll(weekDays)){
                throw new BadRequestException("There is a element that is not a WeekDay");
            }

            enumWeekDays = weekDays.stream()
                    .map(WeekDays::valueOf) // Assuming YourEnum has a valueOf method to convert from String to Enum
                    .collect(Collectors.toList());
        }

        if(weekDays != null && isEnable == null){

            return this.shopRepository.findByWeekDaysIn(pageable, enumWeekDays).map(ShopMapper.INSTANCE::shopToDto);

        }else if(weekDays == null && isEnable != null){

            return this.shopRepository.findByIsEnable(pageable, isEnable).map(ShopMapper.INSTANCE::shopToDto);

        }else if(weekDays != null && isEnable != null){

            return this.shopRepository.findByWeekDaysInAndIsEnable(pageable, enumWeekDays, isEnable).map(ShopMapper.INSTANCE::shopToDto);

        }

        return this.shopRepository.findAll(pageable).map(ShopMapper.INSTANCE::shopToDto);
    }

    public ShopDto getShopById(UUID id) {

        Optional<Shop> maybeOptional = this.shopRepository.findById(id);

        if(maybeOptional.isEmpty()){
            throw new EntityNotFoundException("A Shop with that id does not exist");
        }

        return ShopMapper.INSTANCE.shopToDto(maybeOptional.get());
    }

    public ShopDto createShop(ShopDto shopDto) {

        shopDto.setId(UUID.randomUUID());

        //check hours
        if(shopDto.getStartTime() != null && shopDto.getEndTime() != null){

            if(shopDto.getStartTime().isAfter(shopDto.getEndTime())){
                throw new BadRequestException("The startTime can't be later then the endTime " +
                        "and the endTime can't be before then the startTime and both can't be equal");
            }
        }

        return ShopMapper.INSTANCE.shopToDto(
                this.shopRepository.save(ShopMapper.INSTANCE.dtoToShop(shopDto)));
    }

    public ShopDto updateShop(UUID id, ShopDto shopDto) {

        Optional<Shop> maybeOptional = this.shopRepository.findById(id);

        if(maybeOptional.isEmpty()){
            throw new EntityNotFoundException("A Shop with that id does not exist");
        }

        Shop shop = maybeOptional.get();

        if(shopDto.getIsEnable() != null){
            shop.setIsEnable(shopDto.getIsEnable());
        }

        if(shopDto.getAddress() != null){
            shop.setAddress(shopDto.getAddress());
        }

        if(shopDto.getCity() != null){
            shop.setCity(shopDto.getCity());
        }

        if(shopDto.getCountry() != null){
            shop.setCountry(shopDto.getCountry());
        }

        if(shopDto.getDescription() != null){
            shop.setDescription(shopDto.getDescription());
        }

        if(shopDto.getName() != null){
            shop.setName(shopDto.getName());
        }

        if(shopDto.getPhoneNumber() != null){
            shop.setPhoneNumber(shopDto.getPhoneNumber());
        }

        //check hours
        this.checkHours(shopDto, shop);

        return ShopMapper.INSTANCE.shopToDto(this.shopRepository.save(shop));
    }

    public void deleteShop(UUID id) {

        Optional<Shop> maybeOptional = this.shopRepository.findById(id);

        if(maybeOptional.isEmpty()){
            throw new EntityNotFoundException("A Shop with that id does not exist");
        }

        this.shopRepository.deleteById(id);
        this.producerService.sendMessageUserServiceShopDelete(id);
    }

    private void checkHours(ShopDto shopDto, Shop shop){

        if(shopDto.getEndTime() != null && shopDto.getStartTime() == null){

            if(shopDto.getEndTime().isAfter(shop.getStartTime())){
                shop.setEndTime(shopDto.getEndTime());
            }
            else{
                throw new BadRequestException("The endTime can't be before or equal then the startTime");
            }
        }

        if(shopDto.getStartTime() != null && shopDto.getEndTime() == null){

            if(shopDto.getStartTime().isBefore(shop.getEndTime())){
                shop.setStartTime(shopDto.getStartTime());
            }
            else{
                throw new BadRequestException("The startTime can't be later or equal then the endTime");
            }
        }

        if(shopDto.getStartTime() != null && shopDto.getEndTime() != null){

            if(shopDto.getStartTime().isBefore(shopDto.getEndTime())){

                shop.setStartTime(shopDto.getStartTime());
                shop.setEndTime(shopDto.getEndTime());
            }
            else{
                throw new BadRequestException("The startTime can't be later then the endTime " +
                        "and the endTime can't be before then the startTime and both can't be equal");
            }
        }
    }
}
