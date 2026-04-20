package com.project.commerce.user.service;

//import com.project.commerce.global.exception.CustomException;
import com.project.commerce.user.dto.UserRequestDTO;
import com.project.commerce.user.dto.UserResponseDTO;
import com.project.commerce.user.entity.User;
import com.project.commerce.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserMapper userMapper;

    // CREATE
    public void createUser(UserRequestDTO dto) {
        User user = new User();
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setName(dto.getName());

        userMapper.insertUser(user);
    }

    // READ ALL
    public List<UserResponseDTO> getUsers() {
        return userMapper.findAll()
                .stream()
                .map(user -> UserResponseDTO.builder()
                        .id(user.getId())
                        .email(user.getEmail())
                        .name(user.getName())
                        .build())
                .collect(Collectors.toList());
    }

    // READ ONE
    public UserResponseDTO getUser(Long id) {
        User user = userMapper.findById(id);

//        if (user == null) {
//            throw new CustomException(404, "User not found");
//        }

        return UserResponseDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .name(user.getName())
                .build();
    }

    // UPDATE
    public void updateUser(Long id, UserRequestDTO dto) {
        User user = new User();
        user.setId(id);
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setName(dto.getName());

        userMapper.updateUser(user);
    }

    // DELETE
    public void deleteUser(Long id) {
        userMapper.deleteUser(id);
    }
}