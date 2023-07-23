package kr.richard.userservice.service;

import feign.FeignException;
import kr.richard.userservice.client.OrderServiceClient;
import kr.richard.userservice.dto.UserDto;
import kr.richard.userservice.jpa.UserEntity;
import kr.richard.userservice.jpa.UserRepository;
import kr.richard.userservice.vo.ResponseOrder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final Environment env;
    /*private final RestTemplate restTemplate;*/
    private final OrderServiceClient orderServiceClient;

    @Override
    public void createUser(UserDto userDto) {
        userDto.setUserId(UUID.randomUUID().toString());

        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        UserEntity userEntity = modelMapper.map(userDto, UserEntity.class);
        userEntity.setEncryptedPwd(passwordEncoder.encode(userDto.getPwd()));

        userRepository.save(userEntity);

        modelMapper.map(userEntity, UserDto.class);
    }

    @Override
    public UserDto getUserByUserId(String userId) {
        UserEntity userEntity = userRepository.findByUserId(userId);

        if (userEntity == null){
            throw new UsernameNotFoundException("=== User not found ===");
        }

        UserDto userDto = new ModelMapper().map(userEntity, UserDto.class);

        /*List<ResponseOrder> orders = new ArrayList<>();*/
        /*Using as rest template*/
        /*String orderUrl = String.format(env.getProperty("order_service.url"),userId);
        ResponseEntity<List<ResponseOrder>> orderListResponse
                = restTemplate.exchange(orderUrl, HttpMethod.GET, null,
                new ParameterizedTypeReference<List<ResponseOrder>>() {
                });
        List<ResponseOrder> orderList = orderListResponse.getBody();*/

        /*using as feign client*/
        /*List<ResponseOrder> orderList = null;
        try {
            orderList = orderServiceClient.getOrders(userId);
        }catch (FeignException e){
            log.error(e.getMessage());
        }*/

        /*ErrorDecoder*/
        List<ResponseOrder> orderList = orderServiceClient.getOrders(userId);
        userDto.setOrders(orderList);

        return userDto;
    }

    @Override
    public Iterable<UserEntity> getUserByAll() {
        return userRepository.findAll();
    }

    @Override
    public UserDto getUserDetailsByEmail(String email) {
        UserEntity userEntity = userRepository.findByEmail(email);
        if (userEntity == null){
            throw new UsernameNotFoundException(email);
        }

        return new ModelMapper().map(userEntity, UserDto.class);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByEmail(username);

        if (userEntity == null){
            throw new UsernameNotFoundException(username);
        }

        return new User(userEntity.getEmail(),
                userEntity.getEncryptedPwd(),
                true,
                true,
                true,
                true,
                new ArrayList<>());
    }
}
