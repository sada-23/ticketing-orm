package com.company.service.impl;
import com.company.dto.RoleDTO;
import com.company.mapper.MapperUtil;
import com.company.mapper.RoleMapper;
import com.company.repository.RoleRepository;
import com.company.service.RoleService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService  {

    //injection
    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;
    private final MapperUtil mapperUtil;

    public RoleServiceImpl(RoleRepository roleRepository, RoleMapper roleMapper, MapperUtil mapperUtil) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
        this.mapperUtil = mapperUtil;
    }

    @Override
    public List<RoleDTO> listAllRoles() {

        // bring all roles from DB
//        List<Role> roleList = roleRepository.findAll();

        // convert entity to dto - Mapper
//        roleList.stream().map(obj -> roleMapper.convertToDto(obj));
//        List<RoleDTO> roleDTOList = roleList.stream().map(roleMapper::convertToDto).collect(Collectors.toList());

//        return roleRepository.findAll().stream().map(roleMapper::convertToDto).collect(Collectors.toList());

        return roleRepository.findAll().stream().map(role -> mapperUtil.convert(role,new RoleDTO())).collect(Collectors.toList());
    }

    @Override
    public RoleDTO findById(Long id) {
//        return roleMapper.convertToDto(roleRepository.findById(id).get());

        return mapperUtil.convert(roleRepository.findById(id).get(), new RoleDTO());

    }







}
