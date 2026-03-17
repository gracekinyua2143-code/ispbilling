package com.leavemanagementsystem.leave.dto;


import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Data
@Builder
public class ShiftAvailability {

    private LocalDate date;

    private int shiftACapacity;

    private int shiftAUsed;

    private int shiftARemaining;

    private int shiftBCapacity;

    private int shiftBUsed;

    private int shiftBRemaining;

}
