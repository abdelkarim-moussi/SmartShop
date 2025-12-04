package com.app.smartshop.application.dto;

import lombok.*;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
public class DomainPageRequest {
    private final int page;
    private final int size;
    private final String sortBy;
    private final String sortDir;
}
