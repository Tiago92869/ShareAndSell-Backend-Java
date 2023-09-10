package com.shop.service.services;

import com.shop.service.domain.Rating;
import com.shop.service.domain.Shop;
import com.shop.service.dto.RatingDto;
import com.shop.service.exceptions.EntityNotFoundException;
import com.shop.service.maps.RatingMapper;
import com.shop.service.repositories.RatingRepository;
import com.shop.service.repositories.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RatingService {

    private final RatingRepository ratingRepository;

    private final ShopRepository shopRepository;

    @Autowired
    public RatingService(RatingRepository ratingRepository, ShopRepository shopRepository) {
        this.ratingRepository = ratingRepository;
        this.shopRepository = shopRepository;
    }

    public Page<RatingDto> getAllRatings(Pageable pageable, UUID shopId, UUID userId) {

        if(shopId != null && userId == null){

            return this.ratingRepository.findByShopId(pageable, shopId).map(RatingMapper.INSTANCE::ratingToDto);

        }else if(shopId == null && userId != null){

            return this.ratingRepository.findByUserId(pageable, userId).map(RatingMapper.INSTANCE::ratingToDto);

        }else if(shopId != null && userId != null){

            return this.ratingRepository.findByShopIdAndUserId(pageable, shopId, userId).map(RatingMapper.INSTANCE::ratingToDto);
        }

        return this.ratingRepository.findAll(pageable).map(RatingMapper.INSTANCE::ratingToDto);
    }

    public RatingDto getRatingById(UUID id) {

        Optional<Rating> maybeRating = this.ratingRepository.findById(id);

        if(maybeRating.isEmpty()){
            throw new EntityNotFoundException("A Rating with that id does not exist");
        }

        return RatingMapper.INSTANCE.ratingToDto(maybeRating.get());
    }

    public RatingDto createRatting(RatingDto ratingDto) {

        ratingDto.setId(UUID.randomUUID());

        Optional<Shop> optionalShop = this.shopRepository.findById(ratingDto.getShopId());

        if(optionalShop.isEmpty()){
            throw new EntityNotFoundException("A Shop with that id does not exist");
        }

        Rating rating = RatingMapper.INSTANCE.dtoToRating(ratingDto);
        rating.setShop(optionalShop.get());

        RatingDto finalRating = RatingMapper.INSTANCE.ratingToDto(this.ratingRepository.save(rating));

        this.setRateForShop(rating.getShop().getId());

        return finalRating;
    }

    public RatingDto updateRating(UUID id, RatingDto ratingDto) {

        Optional<Rating> maybeRating = this.ratingRepository.findById(id);

        if(maybeRating.isEmpty()){
            throw new EntityNotFoundException("A Rating with that id does not exist");
        }

        Rating rating = maybeRating.get();

        if(ratingDto.getRate() != null){
            rating.setRate(ratingDto.getRate());
        }

        if(ratingDto.getDescription() != null){
            rating.setDescription(ratingDto.getDescription());
        }

        if(ratingDto.getShopId() != null){

            Optional<Shop> optionalShop = this.shopRepository.findById(ratingDto.getShopId());

            if(optionalShop.isEmpty()){
                throw new EntityNotFoundException("A Shop with that id does not exist");
            }

            rating.setShop(optionalShop.get());
        }

        RatingDto finalRating = RatingMapper.INSTANCE.ratingToDto(this.ratingRepository.save(rating));

        this.setRateForShop(rating.getShop().getId());

        return finalRating;
    }

    public void deleteRating(UUID id) {

        Optional<Rating> maybeRating = this.ratingRepository.findById(id);

        if(maybeRating.isEmpty()){
            throw new EntityNotFoundException("A Rating with that id does not exist");
        }

        this.ratingRepository.deleteById(id);
    }

    private void setRateForShop(UUID shopId){

        Optional<Shop> optionalShop = this.shopRepository.findById(shopId);

        if(optionalShop.isEmpty()){
            throw new EntityNotFoundException("A Shop with that id does not exist");
        }

        List<Rating> ratingList = this.ratingRepository.findByShopId(shopId);

        Float averageRate = (float) ratingList.stream().mapToDouble(Rating::getRate).average().orElse(0.0);

        optionalShop.get().setRate(averageRate);
    }
}
