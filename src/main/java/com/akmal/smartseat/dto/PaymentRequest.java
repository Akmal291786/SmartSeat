package com.akmal.smartseat.dto;
import jakarta.validation.constraints.NotBlank;
public record PaymentRequest(@NotBlank String paymentReference){}
