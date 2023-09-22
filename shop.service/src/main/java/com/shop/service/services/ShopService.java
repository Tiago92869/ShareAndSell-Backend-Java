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

    public Page<ShopDto> getAllShops(Pageable pageable, List<String> weekDays, Boolean isEnable, String search) {

        List<WeekDays> enumWeekDays = new ArrayList<>();

        if(weekDays != null){

            //check if there is an invalid weekDay
            if(!new HashSet<>(this.weekDaysList).containsAll(weekDays)){
                throw new BadRequestException("There is a element that is not a WeekDay");
            }

            enumWeekDays = weekDays.stream()
                    .map(WeekDays::valueOf) // Assuming YourEnum has a valueOf method to convert from String to Enum
                    .collect(Collectors.toList());
        }

        this.producerService.sendMessageLogService("Get all Shops", "45fbf752-1e87-4086-93d3-44e637c26a96");

        if(isEnable == null){

            if(weekDays != null && search == null) {

                return this.shopRepository.findByWeekDaysIn(pageable, enumWeekDays)
                        .map(ShopMapper.INSTANCE::shopToDto);
            }
            else if(weekDays == null && search != null){

                return this.shopRepository.findByNameContainingIgnoreCase(pageable, search)
                        .map(ShopMapper.INSTANCE::shopToDto);
            }
            else if(weekDays != null && search != null){

                return this.shopRepository.findByWeekDaysInAndNameContainingIgnoreCase(pageable, enumWeekDays, search)
                        .map(ShopMapper.INSTANCE::shopToDto);
            }else {
                return this.shopRepository.findAll(pageable).map(ShopMapper.INSTANCE::shopToDto);
            }

        }else {

            if(weekDays != null && search == null) {

                return this.shopRepository.findByWeekDaysInAndIsEnable(pageable, enumWeekDays, isEnable)
                        .map(ShopMapper.INSTANCE::shopToDto);
            }
            else if(weekDays == null && search != null){

                return this.shopRepository.findByIsEnableAndNameContainingIgnoreCase(pageable, isEnable, search)
                        .map(ShopMapper.INSTANCE::shopToDto);
            }
            else if(weekDays != null && search != null){

                return this.shopRepository.findByWeekDaysInAndIsEnableAndNameContainingIgnoreCase(
                        pageable, enumWeekDays, isEnable, search).map(ShopMapper.INSTANCE::shopToDto);
            }
            else {

                return this.shopRepository.findByIsEnable(pageable, isEnable).map(ShopMapper.INSTANCE::shopToDto);
            }
        }
    }

    public ShopDto getShopById(UUID id) {

        Optional<Shop> maybeOptional = this.shopRepository.findById(id);

        if(maybeOptional.isEmpty()){
            throw new EntityNotFoundException("A Shop with that id does not exist");
        }

        this.producerService.sendMessageLogService("Get Shop by Id", "45fbf752-1e87-4086-93d3-44e637c26a96");
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

        this.producerService.sendMessageLogService("Create Shop", "45fbf752-1e87-4086-93d3-44e637c26a96");
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

        this.producerService.sendMessageLogService("Update Shop", "45fbf752-1e87-4086-93d3-44e637c26a96");
        return ShopMapper.INSTANCE.shopToDto(this.shopRepository.save(shop));
    }

    public void deleteShop(UUID id) {

        Optional<Shop> maybeOptional = this.shopRepository.findById(id);

        if(maybeOptional.isEmpty()){
            throw new EntityNotFoundException("A Shop with that id does not exist");
        }

        this.shopRepository.deleteById(id);
        this.producerService.sendMessageLogService("Delete Shop", "45fbf752-1e87-4086-93d3-44e637c26a96");
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
