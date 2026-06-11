package com.hungerbridge.hungerbridge.Dtos;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FoodPostRequest {

    @NotBlank(message = "Food name is Required")
    private String foodName;

    @NotBlank(message = "Quantity is Required")
    private String quantity;

    private String photoUrl;

    @NotNull(message = "Latitude is Required")
    private Double latitude;

    @NotNull(message = "Longitude is Required")
    private Double longitude;

    @NotBlank(message = "Address is Required")
    private String address;

    @NotBlank(message = "Phone number is Required")
    private String providerPhone;

    @NotBlank(message = "Provider name is Required")
    private String providerName;


    private String notes;

    @NotNull(message = "Available until Time is Required")
    private LocalDateTime availableUntil;

}
