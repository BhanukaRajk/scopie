package com.scopie.authservice.dto;

import lombok.*;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PaymentDTO {
    public String userName;
    public Long reservationId;
}
