package com.app.smartshop.application.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@RequiredArgsConstructor
@Builder
public class Page<T> {
    private final List<T> items;
    private final long totalElements;
    private final int totalPages;
}
