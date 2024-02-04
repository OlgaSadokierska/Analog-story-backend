package com.olgasadokierska.analogstory.user.mapper;

import com.olgasadokierska.analogstory.user.dtos.ProductDto;
import com.olgasadokierska.analogstory.user.dtos.ProductDto.ProductDtoBuilder;
import com.olgasadokierska.analogstory.user.model.Product;
import com.olgasadokierska.analogstory.user.model.ProductType;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-02-02T21:33:34+0100",
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

        productDto.model( product.getModel() );
        productDto.brand( product.getBrand() );
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
        product.setModel( productDto.getModel() );
        product.setBrand( productDto.getBrand() );

        return product;
    }

    @Override
    public List<ProductDto> toProductDtoList(List<Product> products) {
        if ( products == null ) {
            return null;
        }

        List<ProductDto> list = new ArrayList<ProductDto>( products.size() );
        for ( Product product : products ) {
            list.add( toProductDto( product ) );
        }

        return list;
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
