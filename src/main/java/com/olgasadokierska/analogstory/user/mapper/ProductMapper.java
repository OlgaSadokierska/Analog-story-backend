package com.olgasadokierska.analogstory.user.mapper;

import com.olgasadokierska.analogstory.user.dtos.ProductDto;
import com.olgasadokierska.analogstory.user.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ProductMapper {

    @Mapping(target = "productTypeId", source = "productType.id")
    ProductDto toProductDto(Product product);

    @Mapping(target = "productType.id", source = "productTypeId")
    Product toProduct(ProductDto productDto);

    @Mapping(target = "userId", source = "user.id")
    ProductDto toProductDtoWithUser(Product product);

    @Mapping(target = "user.id", source = "userId")
    Product toProductWithUser(ProductDto productDto);
}
