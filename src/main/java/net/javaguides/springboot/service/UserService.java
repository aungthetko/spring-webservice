package net.javaguides.springboot.service;

import net.javaguides.springboot.dto.UserDTO;
import net.javaguides.springboot.entity.User;

import java.util.List;

public interface UserService {
    UserDTO createUser(UserDTO user);

    UserDTO getUserById(Long userId);

    List<UserDTO> getAllUsers();

    UserDTO updateUser(UserDTO userDTO);

    void deleteUser(Long userId);
}
