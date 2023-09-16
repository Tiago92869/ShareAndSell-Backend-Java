package com.shop.service;

import com.shop.service.domain.Product;
import com.shop.service.domain.Shop;
import com.shop.service.domain.ShopProduct;
import com.shop.service.dto.ShopProductDto;
import com.shop.service.repositories.ProductRepository;
import com.shop.service.repositories.ShopProductRepository;
import com.shop.service.repositories.ShopRepository;
import com.shop.service.services.ShopProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.when;

public class TestShopProductService {

    @Mock
    private ShopProductRepository shopProductRepository;

    private ShopProductService shopProductService;

    @Mock
    private ShopRepository shopRepository;

    @Mock
    private ProductRepository productRepository;

    private final Shop sampleShop = new Shop(UUID.fromString("f085f23a-d370-4ae8-9b14-74e8077df5ff"));

    private final Product sampleProduct = new Product(UUID.fromString("a9c6998f-e346-4fee-a451-8290bef086fd"),
            "Apple", new byte[111222333], "Fruit", "Apple-image", null);

    private final ShopProduct sampleShopProduct1 = new ShopProduct(
            UUID.fromString("31a78a1a-0863-4cbc-966e-f1faf95b6e41"), true, sampleShop, sampleProduct);

    private final ShopProduct sampleShopProduct2 = new ShopProduct(
            UUID.fromString("3263bb63-20f4-42f4-958a-73dca189c643"), false, sampleShop, sampleProduct);


    private final ShopProductDto sampleShopProductDto = new ShopProductDto(
            UUID.fromString("31a78a1a-0863-4cbc-966e-f1faf95b6e41"), true,
            UUID.fromString("f085f23a-d370-4ae8-9b14-74e8077df5ff"),
            UUID.fromString("a9c6998f-e346-4fee-a451-8290bef086fd"));

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        shopProductService = new ShopProductService(shopProductRepository, productRepository, shopRepository);
    }

    @Test
    public void testGetAllShopProducts(){

        when(shopProductRepository.findAll(any(Pageable.class)))
                .thenReturn(new PageImpl<>(List.of(sampleShopProduct1, sampleShopProduct2)));

        Page<ShopProductDto> result = shopProductService
                .getAllShopProducts(PageRequest.of(0, 10), null, null, null);

        assertNotNull(result);
        assertEquals(2, result.getTotalElements());
    }

    @Test
    public void testGetAllShopProductsShopIdTrue(){

        when(shopProductRepository.findByShopId(any(Pageable.class), eq(sampleShopProductDto.getShopId())))
                .thenReturn(new PageImpl<>(List.of(sampleShopProduct1)));

        Page<ShopProductDto> result = shopProductService
                .getAllShopProducts(PageRequest.of(0, 10), sampleShopProductDto.getShopId(), null, true);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
    }

    @Test
    public void testGetAllShopProductsProductIdTrue(){

        when(shopProductRepository.findByProductId(any(Pageable.class), eq(sampleShopProductDto.getProductId())))
                .thenReturn(new PageImpl<>(List.of(sampleShopProduct1)));

        Page<ShopProductDto> result = shopProductService
                .getAllShopProducts(PageRequest.of(0, 10), null, sampleShopProductDto.getProductId(), true);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
    }

    @Test
    public void testGetAllShopProductsShopIdAndProductIdTrue(){

        when(shopProductRepository.findByShopIdAndProductId(any(Pageable.class), eq(sampleShopProductDto.getShopId()),
                eq(sampleShopProductDto.getProductId()))).thenReturn(new PageImpl<>(List.of(sampleShopProduct1)));

        Page<ShopProductDto> result = shopProductService
                .getAllShopProducts(PageRequest.of(0, 10), sampleShopProductDto.getShopId(),
                        sampleShopProductDto.getProductId(), true);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
    }

    @Test
    public void testGetAllShopProductsShopIdFalse(){

        when(shopProductRepository.findByShopId(any(Pageable.class), eq(sampleShopProductDto.getShopId())))
                .thenReturn(new PageImpl<>(List.of(sampleShopProduct2)));

        Page<ShopProductDto> result = shopProductService
                .getAllShopProducts(PageRequest.of(0, 10), sampleShopProductDto.getShopId(), null, false);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
    }

    @Test
    public void testGetAllShopProductsProductIdFalse(){

        when(shopProductRepository.findByProductId(any(Pageable.class), eq(sampleShopProductDto.getProductId())))
                .thenReturn(new PageImpl<>(List.of(sampleShopProduct2)));

        Page<ShopProductDto> result = shopProductService
                .getAllShopProducts(PageRequest.of(0, 10), null, sampleShopProductDto.getProductId(), false);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
    }

    @Test
    public void testGetAllShopProductsShopIdAndProductIdFalse(){

        when(shopProductRepository.findByShopIdAndProductId(any(Pageable.class), eq(sampleShopProductDto.getShopId()),
                eq(sampleShopProductDto.getProductId()))).thenReturn(new PageImpl<>(List.of(sampleShopProduct2)));

        Page<ShopProductDto> result = shopProductService
                .getAllShopProducts(PageRequest.of(0, 10), sampleShopProductDto.getShopId(),
                        sampleShopProductDto.getProductId(), false);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
    }

    @Test
    public void testGetShopProductById(){

        when(shopProductRepository.findById(sampleShopProductDto.getId()))
                .thenReturn(Optional.of(sampleShopProduct1));

        ShopProductDto result = shopProductService.getShopProductById(sampleShopProductDto.getId());

        assertNotNull(result);
        assertEquals(result.getId(), sampleShopProductDto.getId());
    }

    @Test
    public void testCreateShopProduct(){

        when(shopProductRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        when(shopRepository.findById(sampleShop.getId())).thenReturn(Optional.of(sampleShop));

        when(productRepository.findById(sampleProduct.getId())).thenReturn(Optional.of(sampleProduct));

        ShopProductDto result = shopProductService.createShopProduct(sampleShopProductDto);

        assertNotNull(result);
        assertEquals(result.getShopId(), sampleShopProductDto.getShopId());
    }

    @Test
    public void testUpdateShopProduct(){

        when(shopProductRepository.findById(sampleShopProductDto.getId()))
                .thenReturn(Optional.of(sampleShopProduct1));

        when(shopProductRepository.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        when(shopRepository.findById(sampleShop.getId())).thenReturn(Optional.of(sampleShop));

        when(productRepository.findById(sampleProduct.getId())).thenReturn(Optional.of(sampleProduct));

        ShopProductDto result = shopProductService.updateShopProducts(sampleShopProductDto.getId(), sampleShopProductDto);

        assertNotNull(result);
        assertEquals(result.getProductId(), sampleShopProductDto.getProductId());

    }

    @Test
    public void deleteShopProduct(){

        when(shopProductRepository.findById(sampleShopProductDto.getId())).thenReturn(Optional.of(sampleShopProduct1));

        assertDoesNotThrow(() -> shopProductService.deleteShopProducts(sampleShopProduct1.getId()));
    }
}
