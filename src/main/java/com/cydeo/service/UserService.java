package com.cydeo.service;

import com.cydeo.dto.UserDTO;
import com.cydeo.entity.User;

import java.util.List;

public interface UserService {


    List<UserDTO> listAllUsers();

    UserDTO findByUserNamea(String username);

    UserDTO findById(Long id);

    void save(UserDTO userDTO);

    UserDTO update(UserDTO userDTO);

    void deleteByUserName(String userName);

    void delete(String userName);

    List<UserDTO> findManagers();

    List<UserDTO> findEmployees();
}