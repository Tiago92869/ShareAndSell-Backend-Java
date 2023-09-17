package com.shop.service;

import com.shop.service.domain.Rating;
import com.shop.service.domain.Shop;
import com.shop.service.dto.RatingDto;
import com.shop.service.maps.RatingMapper;
import com.shop.service.repositories.RatingRepository;
import com.shop.service.repositories.ShopRepository;
import com.shop.service.services.RatingService;
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
import static org.mockito.Mockito.*;

public class TestRatingService {

    @Mock
    private RatingRepository ratingRepository;

    private RatingService ratingService;

    @Mock
    private ShopRepository shopRepository;

    private final Shop sampleShop = new Shop(UUID.fromString("f085f23a-d370-4ae8-9b14-74e8077df5ff"));

    private final Rating sampleRating1 = new Rating(UUID.fromString("89d3c769-8f45-41fb-b9dc-e62796f29ff1"),
            (float) 4.4, "Very good service", LocalDate.now(),
            UUID.fromString("6735c697-3a63-4c91-a164-680e98c9f5ea"), sampleShop);

    private final Rating sampleRating2 = new Rating(UUID.fromString("684208c7-6378-4d74-bc16-539bf30dfa40"),
            (float) 4.4, "Very good service", LocalDate.now().minusDays(2),
            UUID.fromString("684208c7-6378-4d74-bc16-539bf30dfa40"), sampleShop);

    private final RatingDto sampleRatingDto = new RatingDto(UUID.fromString("89d3c769-8f45-41fb-b9dc-e62796f29ff1"),
            (float) 4.4, "Very good service", LocalDate.now(),
            UUID.fromString("6735c697-3a63-4c91-a164-680e98c9f5ea"),
            UUID.fromString("f085f23a-d370-4ae8-9b14-74e8077df5ff"));

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        ratingService = new RatingService(ratingRepository, shopRepository);
    }

    @Test
    public void testRatingToDto(){

        RatingDto result = RatingMapper.INSTANCE.ratingToDto(sampleRating1);

        assertNotNull(result);
        assertEquals(result.getUserId(), sampleRating1.getUserId());
        assertEquals(result.getDate(), sampleRating1.getDate());
    }

    @Test
    public void testDtoToRating(){

        Rating result = RatingMapper.INSTANCE.dtoToRating(sampleRatingDto);

        assertNotNull(result);
        assertEquals(result.getUserId(), sampleRatingDto.getUserId());
        assertEquals(result.getDate(), sampleRatingDto.getDate());
    }

    @Test
    public void testGetAllRatings(){

        when(ratingRepository.findAll(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(sampleRating1, sampleRating2)));

        Page<RatingDto> result = ratingService
                .getAllRatings(PageRequest.of(0, 10), null, null);

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
    }

    @Test
    public void testGetAllRatingsShopId(){

        when(ratingRepository.findByShopId(any(Pageable.class), eq(sampleRatingDto.getShopId())))
                .thenReturn(new PageImpl<>(List.of(sampleRating1)));

        Page<RatingDto> result = ratingService
                .getAllRatings(PageRequest.of(0, 10), sampleRatingDto.getShopId(), null);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
    }

    @Test
    public void testGetAllRatingsUserId(){

        when(ratingRepository.findByUserId(any(Pageable.class), eq(sampleRatingDto.getUserId())))
                .thenReturn(new PageImpl<>(List.of(sampleRating1)));

        Page<RatingDto> result = ratingService
                .getAllRatings(PageRequest.of(0, 10), null, sampleRatingDto.getUserId());

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
    }

    @Test
    public void testGetAllRatingsShopIdAndUserId(){

        when(ratingRepository.findByShopIdAndUserId(any(Pageable.class), eq(sampleRatingDto.getShopId()),
                eq(sampleRatingDto.getUserId()))).thenReturn(new PageImpl<>(List.of(sampleRating1)));

        Page<RatingDto> result = ratingService
                .getAllRatings(PageRequest.of(0, 10), sampleRatingDto.getShopId(),
                        sampleRatingDto.getUserId());

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
    }

    @Test
    public void testGetRatingById(){

        when(ratingRepository.findById(sampleRatingDto.getId()))
                .thenReturn(Optional.of(sampleRating1));

        RatingDto result = ratingService.getRatingById(sampleRatingDto.getId());

        assertNotNull(result);
        assertEquals(result.getId(), sampleRatingDto.getId());
    }

    @Test
    public void testCreateRating(){

        when(ratingRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        when(shopRepository.findById(sampleShop.getId())).thenReturn(Optional.of(sampleShop));

        RatingDto result = ratingService.createRatting(sampleRatingDto);

        assertNotNull(result);
        assertEquals(result.getUserId(), sampleRatingDto.getUserId());
    }

    @Test
    public void testUpdateRating(){

        when(ratingRepository.findById(sampleRatingDto.getId()))
                .thenReturn(Optional.of(sampleRating1));

        when(ratingRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        when(shopRepository.findById(sampleShop.getId())).thenReturn(Optional.of(sampleShop));

        RatingDto result = ratingService.updateRating(sampleRatingDto.getId(), sampleRatingDto);

        assertNotNull(result);
        assertEquals(result.getUserId(), sampleRatingDto.getUserId());

    }

    @Test
    public void deleteRating(){

        when(ratingRepository.findById(sampleRatingDto.getId())).thenReturn(Optional.of(sampleRating1));

        assertDoesNotThrow(() -> ratingService.deleteRating(sampleRating1.getId()));
    }
}
