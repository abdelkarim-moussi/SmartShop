package com.app.smartshop.application.dto.order;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class OrderRequestDTO {
    @NotBlank(message = "Client ID is required")
    private String clientId;
    @NotNull
    private List<OrderItemRequestDTO> itemsList;
    private String promotionCode;
}
