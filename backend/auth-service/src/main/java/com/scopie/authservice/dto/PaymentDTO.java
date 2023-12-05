package com.scopie.authservice.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@Data
@NoArgsConstructor
public class PaymentDTO {
    public String userName;
    public boolean paymentStatus;
}
