package com.app.smartshop.domain.model;

import com.app.smartshop.domain.enums.LoyaltyLevel;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Client {
    private String id;
    private String name;
    private String email;
    private LoyaltyLevel loyaltyLevel;
}
