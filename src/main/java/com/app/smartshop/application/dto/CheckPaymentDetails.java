package com.app.smartshop.application.dto;

import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CheckPaymentDetails {
    private String bankName;
    private LocalDate dueDate;
}
