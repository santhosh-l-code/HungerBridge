package com.hungerbridge.hungerbridge.Services;


import com.hungerbridge.hungerbridge.Dtos.ClaimRequest;
import com.hungerbridge.hungerbridge.Dtos.ClaimResponse;
import com.hungerbridge.hungerbridge.Dtos.OtpVerifyRequest;
import com.hungerbridge.hungerbridge.Enums.ClaimStatus;
import com.hungerbridge.hungerbridge.Enums.FoodStatus;
import com.hungerbridge.hungerbridge.Models.FoodPost;
import com.hungerbridge.hungerbridge.Models.VolunteerClaim;
import com.hungerbridge.hungerbridge.Repositories.FoodPostRepository;
import com.hungerbridge.hungerbridge.Repositories.VolunteerClaimRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Random;

@Service
public class ClaimService {

    @Autowired
    private VolunteerClaimRepository volunteerClaimRepo;

    @Autowired
    private FoodPostRepository foodPostRepo;


    @Transactional
    public ClaimResponse claimFood(Long foodPostId, ClaimRequest claimRequest){

        FoodPost foodPost = foodPostRepo.findById(foodPostId)
                .orElseThrow(() -> new RuntimeException("Food Post with id "+ foodPostId + " is not exist"));

        if(foodPost.getStatus() != FoodStatus.AVAILABLE){
            throw new RuntimeException("Food post with id " + foodPostId + " is not available for claiming");
        }

        VolunteerClaim claim = VolunteerClaim.builder()
                .foodPost(foodPost)
                .volunteerName(claimRequest.getVolunteerName())
                .volunteerPhone(claimRequest.getVolunteerPhone())
                .otp(generateOtp())
                .otpVerified(false)
                .build();

        VolunteerClaim volunteerClaim = volunteerClaimRepo.save(claim);

        foodPost.setStatus(FoodStatus.CLAIMED);
        foodPostRepo.save(foodPost);

        return mapToClaimResponse(volunteerClaim);
    }

    public ClaimResponse getClaimById(Long claimId) {
        VolunteerClaim volunteerClaim =
                volunteerClaimRepo.findById(claimId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Claim with id " + claimId + " not found"
                                ));


        return mapToClaimResponse(volunteerClaim);

    }

    @Transactional
    public ClaimResponse verifyOtp(Long claimId, OtpVerifyRequest otpVerifyRequest){
        VolunteerClaim claim =
                volunteerClaimRepo.findById(claimId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Claim with id " + claimId + " not found"
                                ));

        if (claim.getStatus() != ClaimStatus.CLAIMED) {
            throw new RuntimeException(
                    "Claim is not in CLAIMED state"
            );
        }
        if (claim.isOtpVerified()) {
            throw new RuntimeException("OTP already verified");
        }

        if(!claim.getOtp().equals(
                otpVerifyRequest.getOtp()))
        {
            throw new RuntimeException("Invalid OTP");
        }


            claim.setOtpVerified(true);
            claim.setStatus(ClaimStatus.PICKED_UP);
            claim.setPickedUpAt(LocalDateTime.now());

            VolunteerClaim  volunteerClaim = volunteerClaimRepo.save(claim);

        return mapToClaimResponse(volunteerClaim);


    }

    @Transactional
    public ClaimResponse deliverFood(Long claimId) {
        VolunteerClaim claim =
                volunteerClaimRepo.findById(claimId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Claim with id " + claimId + " not found"
                                ));

        if(claim.getStatus() != ClaimStatus.PICKED_UP){
            throw new RuntimeException("Food has not been picked up yet");
        }
        claim.setStatus(ClaimStatus.DELIVERED);
        claim.setDeliveredAt(LocalDateTime.now());

        FoodPost foodPost = claim.getFoodPost();
        foodPost.setStatus(FoodStatus.DELIVERED);

        VolunteerClaim volunteerClaim = volunteerClaimRepo.save(claim);
        foodPostRepo.save(foodPost);

        return mapToClaimResponse(volunteerClaim);

    }

    @Transactional
    public ClaimResponse cancelClaim(Long claimId){
        VolunteerClaim claim =
                volunteerClaimRepo.findById(claimId)
                        .orElseThrow(() ->
                                new RuntimeException(
                                        "Claim with id " + claimId + " not found"
                                ));

        if(claim.getStatus() != ClaimStatus.CLAIMED){
            throw new RuntimeException("Only Claimed food can be cancelled");
        }

        FoodPost foodPost = claim.getFoodPost();
        foodPost.setStatus(FoodStatus.AVAILABLE);


        claim.setStatus(ClaimStatus.CANCELLED);
        claim.setOtpVerified(false);

        VolunteerClaim volunteerClaim = volunteerClaimRepo.save(claim);
        foodPostRepo.save(foodPost);


       return mapToClaimResponse(volunteerClaim);

    }


    private String generateOtp() {
        return String.valueOf(
                100000 + new Random().nextInt(900000)
        );
    }
    private ClaimResponse mapToClaimResponse(VolunteerClaim volunteerClaim){
        return ClaimResponse.builder()
                .id(volunteerClaim.getId())
                .foodPostId(volunteerClaim.getFoodPost().getId())
                .volunteerName(volunteerClaim.getVolunteerName())
                .volunteerPhone(volunteerClaim.getVolunteerPhone())
                .status(volunteerClaim.getStatus())
                .claimedAt(volunteerClaim.getClaimedAt())
                .providerPhone(volunteerClaim.getFoodPost().getProviderPhone())
                .foodName(volunteerClaim.getFoodPost().getName())
                .quantity(volunteerClaim.getFoodPost().getQuantity())
                .address(volunteerClaim.getFoodPost().getAddress())
                .latitude(volunteerClaim.getFoodPost().getLatitude())
                .pickedUpAt(volunteerClaim.getPickedUpAt())
                .deliveredAt(volunteerClaim.getDeliveredAt())
                .longitude(volunteerClaim.getFoodPost().getLongitude())
                .build();

    }

}
