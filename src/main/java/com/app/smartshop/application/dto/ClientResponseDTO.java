package com.app.smartshop.application.dto;

import com.app.smartshop.domain.enums.LoyaltyLevel;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClientResponseDTO {
    private String id;
    private String name;
    private String email;
    private LoyaltyLevel loyaltyLevel;
}
