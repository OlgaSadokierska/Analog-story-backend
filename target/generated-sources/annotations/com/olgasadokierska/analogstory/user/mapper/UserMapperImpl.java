package com.olgasadokierska.analogstory.user.mapper;

import com.olgasadokierska.analogstory.user.dtos.SignUpDto;
import com.olgasadokierska.analogstory.user.dtos.UserDto;
import com.olgasadokierska.analogstory.user.dtos.UserDto.UserDtoBuilder;
import com.olgasadokierska.analogstory.user.model.User;
import com.olgasadokierska.analogstory.user.model.User.UserBuilder;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-01-07T21:59:21+0100",
    comments = "version: 1.4.2.Final, compiler: javac, environment: Java 18.0.1.1 (Oracle Corporation)"
)
@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserDto toUserDto(User user) {
        if ( user == null ) {
            return null;
        }

        UserDtoBuilder userDto = UserDto.builder();

        userDto.id( user.getId() );
        userDto.firstName( user.getFirstName() );
        userDto.lastName( user.getLastName() );
        userDto.email( user.getEmail() );
        userDto.login( user.getLogin() );
        userDto.password( user.getPassword() );
        userDto.phone( user.getPhone() );

        return userDto.build();
    }

    @Override
    public List<UserDto> toUserDtoList(List<User> users) {
        if ( users == null ) {
            return null;
        }

        List<UserDto> list = new ArrayList<UserDto>( users.size() );
        for ( User user : users ) {
            list.add( toUserDto( user ) );
        }

        return list;
    }

    @Override
    public User signUpToUser(SignUpDto signUpDto) {
        if ( signUpDto == null ) {
            return null;
        }

        UserBuilder user = User.builder();

        user.firstName( signUpDto.getFirstName() );
        user.lastName( signUpDto.getLastName() );
        user.email( signUpDto.getEmail() );
        user.login( signUpDto.getLogin() );
        user.phone( signUpDto.getPhone() );

        return user.build();
    }
}
