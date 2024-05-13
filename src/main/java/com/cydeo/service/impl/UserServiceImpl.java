package com.cydeo.service.impl;

import com.cydeo.dto.UserDTO;
import com.cydeo.entity.User;
import com.cydeo.mapper.UserMapper;
import com.cydeo.repository.UserRepository;
import com.cydeo.service.UserService;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Override
    public List<UserDTO> listAllUsers() {
        //
        return userRepository.findAll(Sort.by("firstName")).stream().map(userMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public UserDTO findByUserNamea(String username) {
//        return null;
        return userMapper.convertToDto(userRepository.findByUserName(username));

    }

    @Override
    public UserDTO findById(Long id) {
        return null;
    }

    @Override
    public void save(UserDTO userDTO) {
        userRepository.save(userMapper.convertToEntity(userDTO));
    }

    @Override
    public UserDTO update(UserDTO userDTO) {
//        userRepository.save(userMapper.convertToEntity(userDTO));

        //find current user id
        User user = userRepository.findByUserName(userDTO.getUserName());
        //convert dto
        User convertedUser = userMapper.convertToEntity(userDTO);
        convertedUser.setId(user.getId());
        userRepository.save(convertedUser);

        return userDTO;
    }

    @Override
    public void deleteByUserName(String userName) {
//        userRepository.deleteById(userRepository.findByUserName(userName).getId());
        userRepository.deleteByUserName(userName);
    }

    @Override
    public void delete(String userName) {
        //setting is_deleted=true
        User user = userRepository.findByUserName(userName);
        user.setIsDeleted(true);
        userRepository.save(user);

    }

    @Override
    public List<UserDTO> findManagers() {
        return userRepository.findManagers().stream().map(userMapper::convertToDto).collect(Collectors.toList());
    }
}
