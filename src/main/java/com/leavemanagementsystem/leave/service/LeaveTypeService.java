package com.leavemanagementsystem.leave.service;

import com.leavemanagementsystem.leave.entiy.LeaveType;
import com.leavemanagementsystem.leave.repository.LeaveTypeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LeaveTypeService {

    private final LeaveTypeRepository leaveTypeRepository;

    // CREATE
    public LeaveType create(LeaveType type){
        return leaveTypeRepository.save(type);
    }

    // READ
    public List<LeaveType> getAll(){
        return leaveTypeRepository.findAll();
    }

    public LeaveType getById(Long id){
        return leaveTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Leave type not found"));
    }

    // UPDATE
    public LeaveType update(Long id, LeaveType updatedType){

        LeaveType type = getById(id);

        type.setName(updatedType.getName());
        type.setMaxDays(updatedType.getMaxDays());
        type.setMonthlyLimit(updatedType.getMonthlyLimit());

        return leaveTypeRepository.save(type);
    }

    // DELETE
    public void delete(Long id){
        leaveTypeRepository.deleteById(id);
    }

}