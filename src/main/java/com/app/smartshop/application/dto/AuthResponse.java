package com.app.smartshop.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AuthResponse {
    @JsonProperty("JSESSIONID")
    private String jsessionId;
}
