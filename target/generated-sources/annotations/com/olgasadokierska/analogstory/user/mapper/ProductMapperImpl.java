package com.olgasadokierska.analogstory.user.mapper;

import com.olgasadokierska.analogstory.user.dtos.ProductDto;
import com.olgasadokierska.analogstory.user.dtos.ProductDto.ProductDtoBuilder;
import com.olgasadokierska.analogstory.user.model.Product;
import com.olgasadokierska.analogstory.user.model.ProductType;
import com.olgasadokierska.analogstory.user.model.User;
import com.olgasadokierska.analogstory.user.model.User.UserBuilder;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-01-29T23:36:37+0100",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 18.0.1.1 (Oracle Corporation)"
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
        productDto.brand( product.getBrand() );
        productDto.model( product.getModel() );
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
        product.setBrand( productDto.getBrand() );
        product.setModel( productDto.getModel() );
        product.setDescription( productDto.getDescription() );
        product.setPrice( productDto.getPrice() );

        return product;
    }

    @Override
    public ProductDto toProductDtoWithUser(Product product) {
        if ( product == null ) {
            return null;
        }

        ProductDtoBuilder productDto = ProductDto.builder();

        productDto.userId( productUserId( product ) );
        productDto.brand( product.getBrand() );
        productDto.model( product.getModel() );
        productDto.description( product.getDescription() );
        productDto.price( product.getPrice() );

        return productDto.build();
    }

    @Override
    public Product toProductWithUser(ProductDto productDto) {
        if ( productDto == null ) {
            return null;
        }

        Product product = new Product();

        product.setUser( productDtoToUser( productDto ) );
        product.setBrand( productDto.getBrand() );
        product.setModel( productDto.getModel() );
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

    private Long productUserId(Product product) {
        if ( product == null ) {
            return null;
        }
        User user = product.getUser();
        if ( user == null ) {
            return null;
        }
        Long id = user.getId();
        if ( id == null ) {
            return null;
        }
        return id;
    }

    protected User productDtoToUser(ProductDto productDto) {
        if ( productDto == null ) {
            return null;
        }

        UserBuilder user = User.builder();

        user.id( productDto.getUserId() );

        return user.build();
    }
}
