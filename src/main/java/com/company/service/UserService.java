package com.company.service;
import com.company.dto.UserDTO;

import java.util.List;

// Always Controller calls service layer because of that it should be DTO
public interface UserService {

    List<UserDTO> listAllUsers();
    UserDTO findByUserName(String username);
    void save(UserDTO dto);
    UserDTO update(UserDTO dto);
    void deleteByUserName(String username);
    void delete(String userName);
    List<UserDTO> listAllByRole(String role);




}
