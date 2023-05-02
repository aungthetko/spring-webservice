package net.javaguides.springboot.service.impl;

import lombok.AllArgsConstructor;
import net.javaguides.springboot.dto.UserDTO;
import net.javaguides.springboot.entity.User;
import net.javaguides.springboot.exceptioon.EmailAlreadyTakenException;
import net.javaguides.springboot.exceptioon.UserNotFoundException;
import net.javaguides.springboot.mapper.AutoUserMapper;
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
        Optional<User> optionalUser = userRepository.findByEmail(userDTO.getEmail());
        if(optionalUser.isPresent()){
            throw new EmailAlreadyTakenException("Email Already Taken");
        }
        User user = AutoUserMapper.MAPPER.mapToUser(userDTO);
        User savedUser = userRepository.save(user);
        UserDTO savedUserDTO = AutoUserMapper.MAPPER.mapToUserDto(savedUser);
        return  savedUserDTO;
    }

    @Override
    public UserDTO getUserById(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new UserNotFoundException("User", "id", userId));
        return AutoUserMapper.MAPPER.mapToUserDto(user);

    }

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();
//        return users.stream().map(UserMapper::mapToUserDTO)
//                .collect(Collectors.toList());
//        return users.stream().map(user -> modelMapper.map(user, UserDTO.class))
//                .collect(Collectors.toList());
        return users.stream().map(user -> AutoUserMapper.MAPPER.mapToUserDto(user))
                .collect(Collectors.toList());
    }

    @Override
    public UserDTO updateUser(UserDTO userDTO) {
        User existingUser = userRepository.findById(userDTO.getId())
                .orElseThrow(() ->
                        new UserNotFoundException("User", "id", userDTO.getId()));

        existingUser.setFirstName(userDTO.getFirstName());
        existingUser.setLastName(userDTO.getLastName());
        existingUser.setEmail(userDTO.getEmail());
        User updatedUser = userRepository.save(existingUser);
        // return UserMapper.mapToUserDTO(updatedUser);
        // return modelMapper.map(updatedUser, UserDTO.class);
        return AutoUserMapper.MAPPER.mapToUserDto(updatedUser);
    }

    @Override
    public void deleteUser(Long userId) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() ->
                        new UserNotFoundException("User", "id", userId));
        userRepository.deleteById(userId);
    }
}
