package com.company.service.impl;

import com.company.dto.ProjectDTO;
import com.company.dto.TaskDTO;
import com.company.dto.UserDTO;
import com.company.entity.User;
import com.company.mapper.UserMapper;
import com.company.repository.UserRepository;
import com.company.service.ProjectService;
import com.company.service.TaskService;
import com.company.service.UserService;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
//@Transactional
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final ProjectService projectService;
    private final TaskService taskService;
    private final PasswordEncoder passwordEncoder;

    // ProjectService and UserServiceImpl dependent to each other. Adding @Lazy for ProjectService to prevent circular dependency issue
    public UserServiceImpl(UserRepository userRepository, UserMapper userMapper, @Lazy ProjectService projectService, TaskService taskService, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
        this.projectService = projectService;
        this.taskService = taskService;
        this.passwordEncoder = passwordEncoder;
    }


    @Override
    public List<UserDTO> listAllUsers() {
        List<User> userList = userRepository.findAll(Sort.by("firstName"));
        return userList.stream().map(userMapper::convertToDto).collect(Collectors.toList());
    }

    @Override
    public UserDTO findByUserName(String username) {
//        User user = userRepository.findByUserName(username);
//        return userMapper.convertToDto(user);
        return userMapper.convertToDto(userRepository.findByUserName(username));
    }

    @Override
    public void save(UserDTO dto) {

        dto.setEnabled(true);

        User obj =  userMapper.convertToEntity(dto);
        obj.setPassWord(passwordEncoder.encode(obj.getPassWord()));
        userRepository.save(obj);

//        userRepository.save(userMapper.convertToEntity(dto));

    }

    @Override
    public UserDTO update(UserDTO dto) {

        // find the current user to capture the id
        User user = userRepository.findByUserName(dto.getUserName()); // get user id from the DB
        // Map update user dto to entity object
        User convertedUser = userMapper.convertToEntity(dto); // convert "dto" to entity
        // set id to converted object
        convertedUser.setId(user.getId());// set "convertedUser" id to "user" id
        // save updated user
        userRepository.save(convertedUser); // save "convertedUser" in DB

        return findByUserName(dto.getUserName()); //
    }

    @Override
    public void deleteByUserName(String username) {

        userRepository.deleteByUserName(username);


    }

    @Override
    public void delete(String userName) {
        // We will not delete from db
        // change the flag and keep it on DB

        User user = userRepository.findByUserName(userName);
//        user.setIsDeleted(true);
//        userRepository.save(user); // if data deleted it is still keep the data in DB

        if(checkIfUserCanBeDeleted(user)){
            user.setIsDeleted(true);
            user.setUserName(user.getUserName() + "-" + user.getId());
            userRepository.save(user);
        }

    }

    private boolean checkIfUserCanBeDeleted(User user){

        switch (user.getRole().getDescription()){
            case "Manager":
                List<ProjectDTO> projectDTOList = projectService.readAllByAssignedManager(user);
                return projectDTOList.size() == 0;
            case "Employee":
                List<TaskDTO> taskDTOList = taskService.readAllByAssignedEmployee(user);
                return taskDTOList.size() == 0;
            default:
                return true;


        }

    }

    @Override
    public List<UserDTO> listAllByRole(String role) {
        List<User> users = userRepository.findAllByRoleDescriptionIgnoreCase(role);
        return users.stream().map(userMapper::convertToDto).collect(Collectors.toList());
    }
}
