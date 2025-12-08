package com.app.smartshop.domain.entity.search;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientCriteria {
    private String loyaltyLevel;
    private String search;
    private String name;
}
