package com.olgasadokierska.analogstory.user.mapper;

import com.olgasadokierska.analogstory.user.dtos.ProductDto;
import com.olgasadokierska.analogstory.user.model.Product;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ProductMapper {
    @Mapping(target = "model", source = "product.model")
    @Mapping(target = "brand", source = "product.brand")
    @Mapping(target = "productTypeId", source = "productType.id")
    ProductDto toProductDto(Product product);

    @Mapping(target = "productType.id", source = "productTypeId")
    Product toProduct(ProductDto productDto);

    List<ProductDto> toProductDtoList(List<Product> products);


}
