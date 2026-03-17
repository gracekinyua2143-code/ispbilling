package com.leavemanagementsystem.leave.service;



import com.leavemanagementsystem.leave.entiy.Role;
import com.leavemanagementsystem.leave.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public Role create(Role role){
        return roleRepository.save(role);
    }

    public List<Role> all(){
        return roleRepository.findAll();
    }

}