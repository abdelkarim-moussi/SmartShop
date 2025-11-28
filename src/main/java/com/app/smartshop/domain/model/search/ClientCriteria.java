package com.app.smartshop.domain.model.search;

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
