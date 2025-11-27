package com.app.smartshop.application.dto.client;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Filters {
    private String loyaltyLevel;
    private String search;
}
