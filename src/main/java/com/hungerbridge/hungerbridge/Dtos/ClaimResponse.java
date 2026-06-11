package com.hungerbridge.hungerbridge.Dtos;


import com.hungerbridge.hungerbridge.Enums.ClaimStatus;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClaimResponse {

    private Long id;
    private Long foodPostId;

    private String volunteerName;
    private String volunteerPhone;
    private ClaimStatus status;

    private LocalDateTime claimedAt;
    private LocalDateTime pickedUpAt;
    private LocalDateTime deliveredAt;

    private String providerPhone;

    private String foodName;
    private String quantity;
    private String address;
    private Double latitude;
    private Double longitude;

}
