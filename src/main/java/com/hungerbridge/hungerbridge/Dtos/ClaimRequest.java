package com.hungerbridge.hungerbridge.Dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClaimRequest {


    @NotBlank(message = "Volunteer name is Required")
    private String volunteerName;

    @NotBlank(message = "Volunteer phone is Required")
    private String volunteerPhone;


}
