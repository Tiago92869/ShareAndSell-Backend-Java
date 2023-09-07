package com.shop.service.services;
import com.shop.service.domain.Shop;
import com.shop.service.dto.ShopDto;
import com.shop.service.exceptions.EntityNotFoundException;
import com.shop.service.maps.ShopMapper;
import com.shop.service.repositories.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.UUID;

@Service
public class ShopService {

    private final ShopRepository shopRepository;

    @Autowired
    public ShopService(ShopRepository shopRepository) {
        this.shopRepository = shopRepository;
    }

    public Page<ShopDto> getAllShops(Pageable pageable) {

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

        if(shopDto.getEndTime() != null){
            shop.setEndTime(shopDto.getEndTime());
        }

        if(shopDto.getStartTime() != null){
            shop.setStartTime(shopDto.getStartTime());
        }

        if(shopDto.getName() != null){
            shop.setName(shopDto.getName());
        }

        if(shopDto.getPhoneNumber() != null){
            shop.setPhoneNumber(shopDto.getPhoneNumber());
        }

        if(shopDto.getRate() != null){
            shop.setRate(shopDto.getRate());
        }

        return ShopMapper.INSTANCE.shopToDto(shop);
    }

    public void deleteShop(UUID id) {

        Optional<Shop> maybeOptional = this.shopRepository.findById(id);

        if(maybeOptional.isEmpty()){
            throw new EntityNotFoundException("A Shop with that id does not exist");
        }

        this.shopRepository.deleteById(id);
    }
}
