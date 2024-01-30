package com.olgasadokierska.analogstory.user.mapper;

import com.olgasadokierska.analogstory.user.dtos.ProductDto;
import com.olgasadokierska.analogstory.user.dtos.ProductDto.ProductDtoBuilder;
import com.olgasadokierska.analogstory.user.model.Product;
import com.olgasadokierska.analogstory.user.model.ProductType;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-01-30T10:51:25+0100",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 20.0.2 (Oracle Corporation)"
)
@Component
public class ProductMapperImpl implements ProductMapper {

    @Override
    public ProductDto toProductDto(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductDtoBuilder productDto = ProductDto.builder();

        productDto.productTypeId( productProductTypeId( product ) );
        productDto.id( product.getId() );
        productDto.description( product.getDescription() );
        productDto.price( product.getPrice() );

        return productDto.build();
    }

    @Override
    public Product toProduct(ProductDto productDto) {
        if ( productDto == null ) {
            return null;
        }

        Product product = new Product();

        product.setProductType( productDtoToProductType( productDto ) );
        product.setId( productDto.getId() );
        product.setDescription( productDto.getDescription() );
        product.setPrice( productDto.getPrice() );

        return product;
    }

    private Long productProductTypeId(Product product) {
        if ( product == null ) {
            return null;
        }
        ProductType productType = product.getProductType();
        if ( productType == null ) {
            return null;
        }
        Long id = productType.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected ProductType productDtoToProductType(ProductDto productDto) {
        if ( productDto == null ) {
            return null;
        }

        ProductType productType = new ProductType();

        productType.setId( productDto.getProductTypeId() );

        return productType;
    }
}
