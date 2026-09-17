package com.akmal.smartseat.dto;
import jakarta.validation.constraints.*;
public record ReserveRequest(@NotBlank String seatNumber,@NotBlank String customerName,@Email @NotBlank String customerEmail){}
