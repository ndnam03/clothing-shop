package com.example.service.impl;

import com.example.entity.Role;
import com.example.entity.User;
import com.example.exception.OnlineShopException;
import com.example.jwt.JwtTokenProvider;
import com.example.mapper.UserMapper;
import com.example.payload.request.LoginDTORequest;
import com.example.payload.request.UserDTOCreate;
import com.example.payload.response.BaseResponse;
import com.example.payload.response.LoginDTOResponse;
import com.example.payload.response.UserDTOResponse;
import com.example.repository.UserRepository;
import com.example.security.CustomUserDetails;
import com.example.service.RoleService;
import com.example.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.*;


@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserServiceImpl implements UserService {


    UserRepository userRepository;
    JwtTokenProvider jwtTokenProvider;
    RoleService roleService;
    PasswordEncoder passwordEncoder;


    @Override
    public BaseResponse createAccount(UserDTOCreate userDTOCreate) {
        BaseResponse baseResponse = new BaseResponse();
        List<String> roleNames = userDTOCreate.getRoles();
        Set<Role> listRoles = new HashSet<>();

        Map<String, String> roleMap = new HashMap<>();
        roleMap.put("admin", "ADMIN");
        roleMap.put("moderator", "MODERATOR");
        roleMap.put("user", "USER");

        if (roleNames == null || roleNames.isEmpty()) {
            Role defaultRole = roleService.findByRoleName("USER")
                    .orElseThrow(() -> new RuntimeException("Error: Role 'ROLE_USER' is not found"));
            listRoles.add(defaultRole);
        } else {
            for (String roleName : roleNames) {
                String normalizedRoleName = roleName.toLowerCase();
                if (!roleMap.containsKey(normalizedRoleName)) {
                    throw new IllegalArgumentException("Invalid role: " + roleName);
                }
                Role role = roleService.findByRoleName(roleMap.get(normalizedRoleName))
                        .orElseThrow(() -> new RuntimeException("Error: Role 'ROLE_" + normalizedRoleName.toUpperCase() + "' is not found"));
                listRoles.add(role);
            }
        }



        BaseResponse validateResult = validateUser(userDTOCreate);
        if (validateResult.isSuccess()) {
            User user = UserMapper.toUser(userDTOCreate);
            user.setPassword(passwordEncoder.encode(userDTOCreate.getPassword()));
            user.setRoles(listRoles);
            userRepository.save(user);
            UserDTOResponse response = UserMapper.userDTOResponse(user);

            baseResponse.setSuccess(true);
            baseResponse.setCode(String.valueOf(HttpStatus.OK));
            baseResponse.setData(response);
        } else {
            baseResponse.setCode(validateResult.getCode());
            baseResponse.setSuccess(validateResult.isSuccess());
            baseResponse.setData(validateResult.getData());
        }

        return baseResponse;

    }

    @Override
    public BaseResponse login(LoginDTORequest loginDTORequest) {

        BaseResponse baseResponse = new BaseResponse();
        //lay account theo username
        User user = userRepository.findByUsername(loginDTORequest.getUsername())
                .orElseThrow(() -> new OnlineShopException("Username or password is incorrect"));
        boolean isAuthentication = passwordEncoder.matches(loginDTORequest.getPassword(),user.getPassword());
        if(!isAuthentication){
            throw new OnlineShopException("Username or password is incorrect");
        }
        String accessToken = jwtTokenProvider.generateToken(CustomUserDetails.mapUserToUserDetail(user));
        LoginDTOResponse loginDTOResponse = UserMapper.tokenPayLoad(user);
        loginDTOResponse.setToken(accessToken);
        baseResponse.setCode(String.valueOf(HttpStatus.OK));
        baseResponse.setSuccess(true);
        baseResponse.setData(loginDTOResponse);
        return baseResponse;
    }



    private BaseResponse validateUser(UserDTOCreate userDTO) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.setSuccess(true);
        Optional<User> user = userRepository.findByUsername(userDTO.getUsername());

        if (!ObjectUtils.isEmpty(user)) {
            baseResponse.setSuccess(false);
            baseResponse.setData("User name is not exits");
            baseResponse.setCode(String.valueOf(HttpStatus.BAD_REQUEST));
            return baseResponse;
        }

        return baseResponse;

    }


}
