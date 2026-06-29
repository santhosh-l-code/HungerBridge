package com.hungerbridge.hungerbridge.Controllers;


import com.hungerbridge.hungerbridge.Dtos.ClaimRequest;
import com.hungerbridge.hungerbridge.Dtos.ClaimResponse;
import com.hungerbridge.hungerbridge.Dtos.OtpVerifyRequest;
import com.hungerbridge.hungerbridge.Services.ClaimService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/claim")
public class ClaimController {

    @Autowired
    private ClaimService claimService;

    @PostMapping("/{foodPostId}")
    public ClaimResponse claimFood(@PathVariable Long foodPostId,
                                   @Valid @RequestBody ClaimRequest claimRequest){

        return claimService.claimFood(foodPostId,claimRequest);
    }

    @GetMapping("/{claimId}")
    public ClaimResponse getClaimById(@PathVariable Long claimId){
        return claimService.getClaimById(claimId);
    }

    @PostMapping("/{claimId}/verify-otp")
    public ClaimResponse verifyOtp(@PathVariable Long claimId,
                             @Valid @RequestBody OtpVerifyRequest otpVerifyRequest){
        return claimService.verifyOtp(claimId,otpVerifyRequest);
    }

    @PostMapping("/{claimId}/deliver")
    public ClaimResponse deliverFood(@PathVariable Long claimId){
        return claimService.deliverFood(claimId);
    }

    @DeleteMapping("/{claimId}")
    public ClaimResponse cancelClaim(@PathVariable Long claimId){
        return claimService.cancelClaim(claimId);
    }
}
