package com.shop.service.services;
import com.shop.service.domain.Product;
import com.shop.service.domain.Shop;
import com.shop.service.domain.ShopProduct;
import com.shop.service.dto.ShopProductDto;
import com.shop.service.exceptions.EntityNotFoundException;
import com.shop.service.maps.AppointmentMapper;
import com.shop.service.maps.ShopProductMapper;
import com.shop.service.repositories.ProductRepository;
import com.shop.service.repositories.ShopProductRepository;
import com.shop.service.repositories.ShopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ShopProductService {

    private final ShopProductRepository shopProductRepository;

    private final ProductRepository productRepository;

    private final ShopRepository shopRepository;

    @Autowired
    public ShopProductService(ShopProductRepository shopProductRepository, ProductRepository productRepository, ShopRepository shopRepository) {
        this.shopProductRepository = shopProductRepository;
        this.productRepository = productRepository;
        this.shopRepository = shopRepository;
    }

    public Page<ShopProductDto> getAllShopProducts(Pageable pageable, UUID shopId, UUID productId, Boolean isEnable) {

        if(isEnable != null){

            if(shopId != null && productId == null){

                return this.shopProductRepository.findByShopId(pageable, shopId).map(ShopProductMapper.INSTANCE::shopProductToDto);

            }else if(shopId == null && productId != null){

                return this.shopProductRepository.findByProductId(pageable, productId).map(ShopProductMapper.INSTANCE::shopProductToDto);

            }else if(shopId != null && productId != null){

                return this.shopProductRepository.findByShopIdAndProductId(pageable, shopId, productId).map(ShopProductMapper.INSTANCE::shopProductToDto);
            }

        }else {

            if(shopId != null && productId == null){

                return this.shopProductRepository.findByShopIdAndIsEnable(pageable, shopId, isEnable).map(ShopProductMapper.INSTANCE::shopProductToDto);

            }else if(shopId == null && productId != null){

                return this.shopProductRepository.findByProductIdAndIsEnable(pageable, productId, isEnable).map(ShopProductMapper.INSTANCE::shopProductToDto);

            }else if(shopId != null && productId != null){

                return this.shopProductRepository.findByShopIdAndProductIdAndIsEnable(pageable, shopId, productId, isEnable).map(ShopProductMapper.INSTANCE::shopProductToDto);
            }
        }

        return this.shopProductRepository.findAll(pageable).map(ShopProductMapper.INSTANCE::shopProductToDto);
    }

    public ShopProductDto getShopProductById(UUID id) {

        Optional<ShopProduct> optionalShopProduct = this.shopProductRepository.findById(id);

        if(optionalShopProduct.isEmpty()){
            throw new EntityNotFoundException("A ShopProduct with that id does not exist");
        }

        return ShopProductMapper.INSTANCE.shopProductToDto(optionalShopProduct.get());
    }

    public ShopProductDto createShopProduct(ShopProductDto shopProductDto) {

        shopProductDto.setId(UUID.randomUUID());

        Optional<Shop> optionalShop = this.shopRepository.findById(shopProductDto.getShopId());

        if(optionalShop.isEmpty()){
            throw new EntityNotFoundException("A Shop with that id does not exist");
        }

        Optional<Product> optionalProduct = this.productRepository.findById(shopProductDto.getProductId());

        if(optionalProduct.isEmpty()){
            throw new EntityNotFoundException("A Product with that id does not exist");
        }

        ShopProduct shopProduct = ShopProductMapper.INSTANCE.dtoToShopProduct(shopProductDto);
        shopProduct.setProduct(optionalProduct.get());
        shopProduct.setShop(optionalShop.get());

        return ShopProductMapper.INSTANCE.shopProductToDto(this.shopProductRepository.save(shopProduct));
    }

    public ShopProductDto updateShopProducts(UUID id, ShopProductDto shopProductDto) {

        Optional<ShopProduct> optionalShopProduct = this.shopProductRepository.findById(id);

        if(optionalShopProduct.isEmpty()){
            throw new EntityNotFoundException("A ShopProduct with that id does not exist");
        }

        ShopProduct shopProduct = optionalShopProduct.get();

        if(shopProductDto.getProductId() != null){

            Optional<Product> optionalProduct = this.productRepository.findById(shopProductDto.getProductId());

            if(optionalProduct.isEmpty()){
                throw new EntityNotFoundException("A Product with that id does not exist");
            }

            shopProduct.setProduct(optionalProduct.get());
        }

        if(shopProductDto.getShopId() != null){

            Optional<Shop> optionalShop = this.shopRepository.findById(shopProductDto.getShopId());

            if(optionalShop.isEmpty()){
                throw new EntityNotFoundException("A Shop with that id does not exist");
            }

            shopProduct.setShop(optionalShop.get());
        }

        if(shopProductDto.getIsEnable() != null){

            shopProduct.setIsEnable(shopProductDto.getIsEnable());
        }

        return ShopProductMapper.INSTANCE.shopProductToDto(this.shopProductRepository.save(shopProduct));
    }

    public void deleteShopProducts(UUID id) {

        Optional<ShopProduct> optionalShopProduct = this.shopProductRepository.findById(id);

        if(optionalShopProduct.isEmpty()){
            throw new EntityNotFoundException("A ShopProduct with that id does not exist");
        }

        this.shopProductRepository.deleteById(id);
    }
}
