package kr.richard.userservice.controller;

import kr.richard.userservice.dto.UserDto;
import kr.richard.userservice.jpa.UserEntity;
import kr.richard.userservice.service.UserService;
import kr.richard.userservice.vo.Greeting;
import kr.richard.userservice.vo.RequestUser;
import kr.richard.userservice.vo.ResponseUser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/")
public class UserController {

    private final Environment environment;
    private final Greeting greeting;
    private final UserService userService;

    @GetMapping("/health_check")
    public String status(){
        return String.format("It's Working in User Service"
                + " / port(local.server.port)="+ environment.getProperty("local.server.port")
                + " / port(server.port)="+ environment.getProperty("server.port")
                + " / token secret="+ environment.getProperty("token.secret")
                + " / token expiration time="+ environment.getProperty("token.expiration_time"));
    }

    @GetMapping("/welcome")
    public String welcome(){
        return greeting.getMessage();
    }

    @PostMapping("/users")
    public ResponseEntity<ResponseUser> createUser(@RequestBody RequestUser requestUser, ModelMapper mapper){

        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserDto userDto = mapper.map(requestUser, UserDto.class);
        userService.createUser(userDto);
        ResponseUser responseUser = mapper.map(userDto, ResponseUser.class);

        return ResponseEntity.status(HttpStatus.CREATED).body(responseUser);

    }

    @GetMapping("/users")
    public ResponseEntity<List<ResponseUser>> getUsers(){
        Iterable<UserEntity> userList = userService.getUserByAll();

        List<ResponseUser> result = new ArrayList<>();
        userList.forEach(v -> result.add(new ModelMapper().map(v, ResponseUser.class)));

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }

    @GetMapping("/users/{userId}")
    public ResponseEntity<ResponseUser> getUser(@PathVariable("userId") String userId){
        UserDto userDto;
        userDto = userService.getUserByUserId(userId);
        ResponseUser resultUser = new ModelMapper().map(userDto, ResponseUser.class);

        return ResponseEntity.status(HttpStatus.OK).body(resultUser);
    }


}
