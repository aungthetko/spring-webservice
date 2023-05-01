package net.javaguides.springboot.service.impl;

import lombok.AllArgsConstructor;
import net.javaguides.springboot.dto.UserDTO;
import net.javaguides.springboot.entity.User;
import net.javaguides.springboot.mapper.UserMapper;
import net.javaguides.springboot.repository.UserRepository;
import net.javaguides.springboot.service.UserService;
import org.apache.logging.log4j.util.Strings;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public UserDTO createUser(UserDTO userDTO) {
        // User user = UserMapper.mapToUser(userDTO);
        User user = modelMapper.map(userDTO, User.class);
        User savedUser = userRepository.save(user);
        // UserDTO savedUserDTO = UserMapper.mapToUserDTO(savedUser);
        UserDTO savedUserDTO = modelMapper.map(savedUser, UserDTO.class);
        return  savedUserDTO;
    }

    @Override
    public UserDTO getUserById(Long userId) {
        Optional<User> optionalUser = userRepository.findById(userId);
        User user = optionalUser.get();
        // return UserMapper.mapToUserDTO(user);
        return modelMapper.map(user, UserDTO.class);
    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
//        return users.stream().map(UserMapper::mapToUserDTO)
//                .collect(Collectors.toList());
        return users.stream().map(user -> modelMapper.map(user, UserDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO) {
        User existingUser = userRepository.findById(userDTO.getId()).get();
        existingUser.setFirstName(userDTO.getFirstName());
        existingUser.setLastName(userDTO.getLastName());
        existingUser.setEmail(userDTO.getEmail());
        User updatedUser = userRepository.save(existingUser);
        // return UserMapper.mapToUserDTO(updatedUser);
        return modelMapper.map(updatedUser, UserDTO.class);
    }

    @Override
    public void deleteUser(Long userId) {
        userRepository.deleteById(userId);
    }
}
