package com.app.smartshop.application.dto;

import lombok.*;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DomainPageRequest {
    private int page;
    private int size;
    private String sortBy;
    private String sortDir;
}
