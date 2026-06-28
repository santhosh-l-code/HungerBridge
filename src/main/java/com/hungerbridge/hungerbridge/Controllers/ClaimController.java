package com.hungerbridge.hungerbridge.Controllers;


import com.hungerbridge.hungerbridge.Dtos.ClaimRequest;
import com.hungerbridge.hungerbridge.Dtos.ClaimResponse;
import com.hungerbridge.hungerbridge.Services.ClaimService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/claim")
public class ClaimController {

    @Autowired
    private ClaimService claimService;

    @PostMapping("/{foodPostId}")
    public ClaimResponse claimFood(@PathVariable Long foodPostId,
                                   @RequestBody ClaimRequest claimRequest){

        return claimService.claimFood(foodPostId,claimRequest);
    }
}
