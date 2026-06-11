package com.hungerbridge.hungerbridge.Dtos;


import com.hungerbridge.hungerbridge.Enums.FoodStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FoodPostResponse {

    private Long id;
    private String foodName;
    private String quantity;
    private String photoUrl;
    private Double latitude;
    private Double longitude;
    private FoodStatus status;
    private LocalDateTime createdAt;
    private String address;
    private String providerName;
    private String notes;
    private LocalDateTime availableUntil;

    // Distance from volunteer — only populated in nearby search
    private Double distanceKm;

}
