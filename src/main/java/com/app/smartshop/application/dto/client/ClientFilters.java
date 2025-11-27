package com.app.smartshop.application.dto.client;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientFilters {
    private String loyaltyLevel;
    private String search;
}
