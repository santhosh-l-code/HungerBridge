package com.hungerbridge.hungerbridge.Services;


import com.hungerbridge.hungerbridge.Dtos.ClaimRequest;
import com.hungerbridge.hungerbridge.Dtos.ClaimResponse;
import com.hungerbridge.hungerbridge.Enums.ClaimStatus;
import com.hungerbridge.hungerbridge.Enums.FoodStatus;
import com.hungerbridge.hungerbridge.Models.FoodPost;
import com.hungerbridge.hungerbridge.Models.VolunteerClaim;
import com.hungerbridge.hungerbridge.Repositories.FoodPostRepository;
import com.hungerbridge.hungerbridge.Repositories.VolunteerClaimRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
                .otp("123456")
                .otpVerified(false)
                .build();

        VolunteerClaim volunteerClaim = volunteerClaimRepo.save(claim);

        foodPost.setStatus(FoodStatus.CLAIMED);
        foodPostRepo.save(foodPost);

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
                .longitude(volunteerClaim.getFoodPost().getLongitude())
                .build();
    }
}
