package com.zinoviev.data.mapping;

import com.zinoviev.entity.data.User;
import com.zinoviev.entity.dto.data.UserDto;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper( UserMapper.class );

    //@Mapping(source = "numberOfSeats", target = "seatCount")
    UserDto userToUserDto(User user);
    User userDtoToUser(UserDto user);
}
