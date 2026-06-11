package com.hungerbridge.hungerbridge.Dtos;


import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OtpVerifyRequest {

//    private Long claimId;  I think we will get the claim id by the url

    @NotBlank(message = "OTP is Required")
    private String otp;

}
