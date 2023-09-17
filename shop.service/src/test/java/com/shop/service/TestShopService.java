package com.shop.service;

import com.shop.service.domain.Shop;
import com.shop.service.domain.WeekDays;
import com.shop.service.dto.ShopDto;
import com.shop.service.maps.ShopMapper;
import com.shop.service.repositories.ShopRepository;
import com.shop.service.services.ShopService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalTime;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

public class TestShopService {

    private final List<WeekDays> weekDaysList = new ArrayList<>(Collections.singleton(WeekDays.MONDAY));

    private final List<WeekDays> weekDaysList2 = new ArrayList<>(Collections.singleton(WeekDays.SUNDAY));

    private final List<String> weekDayListString = List.of("MONDAY");

    @Mock
    private ShopRepository shopRepository;

    private ShopService shopService;

    private final Shop sampleShop1 = new Shop(UUID.fromString("1604ddbc-93f6-4a12-b497-8f0ec795e33a"), "Shop1",
            "Shop number 1", "Bankers Street", "Braga", "Portugal", "+351485265471",
            (float) 4.4, LocalTime.now().minusHours(2), LocalTime.now().minusHours(1),
            true, null, null, null, weekDaysList);

    private final Shop sampleShop2 = new Shop(UUID.fromString("629af1ca-71b3-4cf0-83b7-1fcef134945e"), "Shop2",
            "Shop number 2", "Shops Street", "Porto", "Portugal", "+351265265987",
            (float) 4.4, LocalTime.now().minusHours(2), LocalTime.now().minusHours(1),
            true, null, null, null, weekDaysList2);

    private final ShopDto sampleShopDto = new ShopDto(UUID.fromString("1604ddbc-93f6-4a12-b497-8f0ec795e33a"), "Shop1",
            "Shop number 1", "Bankers Street", "Braga", "Portugal", "+351485265471",
            (float) 4.4, LocalTime.now().minusHours(2), LocalTime.now().minusHours(1),
            true, weekDaysList);

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        shopService = new ShopService(shopRepository);
    }

    @Test
    public void testShopToDto(){

        ShopDto result = ShopMapper.INSTANCE.shopToDto(sampleShop1);

        assertNotNull(result);
        assertEquals(result.getId(), sampleShop1.getId());
        assertEquals(result.getDescription(), sampleShop1.getDescription());
    }

    @Test
    public void testDtoToShop(){

        Shop result = ShopMapper.INSTANCE.dtoToShop(sampleShopDto);

        assertNotNull(result);
        assertEquals(result.getId(), sampleShopDto.getId());
        assertEquals(result.getDescription(), sampleShopDto.getDescription());
    }

    @Test
    public void testGetAllShops(){

        when(shopRepository.findAll(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(sampleShop1, sampleShop2)));

        Page<ShopDto> result = shopService.getAllShops(PageRequest.of(0, 10), null, null);

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
    }

    @Test
    public void testGetAllShopsWeek(){

        when(shopRepository.findByWeekDaysIn(any(Pageable.class), eq(weekDaysList)))
                .thenReturn(new PageImpl<>(List.of(sampleShop1)));

        Page<ShopDto> result = shopService.getAllShops(PageRequest.of(0, 10), weekDayListString, null);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
    }

    @Test
    public void testGetAllShopsTrue(){

        when(shopRepository.findByIsEnable(any(Pageable.class), eq(true)))
                .thenReturn(new PageImpl<>(List.of(sampleShop1, sampleShop2)));

        Page<ShopDto> result = shopService.getAllShops(PageRequest.of(0, 10), null, true);

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
    }

    @Test
    public void testGetAllShopsWeekTrue(){

        when(shopRepository.findByWeekDaysInAndIsEnable(any(Pageable.class), eq(weekDaysList), eq(true)))
                .thenReturn(new PageImpl<>(List.of(sampleShop1)));

        Page<ShopDto> result = shopService.getAllShops(PageRequest.of(0, 10), weekDayListString, true);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
    }

    @Test
    public void testGetShopById(){

        when(shopRepository.findById(UUID.fromString("a9c6998f-e346-4fee-a451-8290bef086fd")))
                .thenReturn(Optional.of(sampleShop1));

        ShopDto result = shopService.getShopById(UUID.fromString("a9c6998f-e346-4fee-a451-8290bef086fd"));

        assertNotNull(result);
        assertEquals(result.getDescription(), sampleShop1.getDescription());
    }

    @Test
    public void testCreateShop(){

        when(shopRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        ShopDto result = shopService.createShop(sampleShopDto);

        assertNotNull(result);
        assertNotNull(result.getId());
        assertEquals(result.getDescription(), sampleShopDto.getDescription());
    }

    @Test
    public void testUpdateShop(){

        when(shopRepository.findById(sampleShopDto.getId())).thenReturn(Optional.of(sampleShop1));

        when(shopRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        ShopDto result = shopService.updateShop(sampleShopDto.getId(), sampleShopDto);

        assertNotNull(result);
        assertEquals(result.getDescription(), sampleShopDto.getDescription());
    }

    @Test
    public void testDeleteShop(){

        when(shopRepository.findById(sampleShopDto.getId())).thenReturn(Optional.of(sampleShop1));

        assertDoesNotThrow(() -> shopService.deleteShop(sampleShopDto.getId()));
    }
}
