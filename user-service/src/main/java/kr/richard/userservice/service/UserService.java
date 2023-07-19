package kr.richard.userservice.service;

import kr.richard.userservice.dto.UserDto;
import kr.richard.userservice.jpa.UserEntity;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService extends UserDetailsService {
    void createUser(UserDto userDto);

    UserDto getUserByUserId(String userId);

    Iterable<UserEntity> getUserByAll();

    UserDto getUserDetailsByEmail(String userName);
}
