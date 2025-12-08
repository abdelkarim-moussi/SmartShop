package com.app.smartshop.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Entity
@Table(name = "promo_codes")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PromoCode {
    @Id
    private String id;
    @Pattern(regexp = "PROMO-[A-Z0-9]{4}")
    private String code;
    private int appliedTimes;
}
