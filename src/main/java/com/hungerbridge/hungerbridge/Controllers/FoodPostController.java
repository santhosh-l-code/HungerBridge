package com.hungerbridge.hungerbridge.Controllers;


import com.hungerbridge.hungerbridge.Dtos.FoodPostRequest;
import com.hungerbridge.hungerbridge.Dtos.FoodPostResponse;
import com.hungerbridge.hungerbridge.Services.FoodPostService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// ResponseBody + Controller
@RestController
@RequestMapping("/api/food")
public class FoodPostController {

    @Autowired
    private FoodPostService foodPostService;

    @PostMapping
    public FoodPostResponse createFoodPost(@Valid @RequestBody FoodPostRequest foodPostRequest){
        return foodPostService.createFoodPost(foodPostRequest);
    }

    @GetMapping("/{id}")
    public FoodPostResponse getFoodPostById(@PathVariable Long id){
        return foodPostService.getFoodPostById(id);
    }

    @DeleteMapping("/{id}")
    public String deleteFoodPostById(@PathVariable Long id){
        foodPostService.deleteFoodPostById(id);
        return "Food post id "+id+" deleted Successfully..";
    }

    @GetMapping("/nearby")
    public List<FoodPostResponse> findNearbyFoodPosts(
            @RequestParam Double lat,
            @RequestParam Double lng,
            @RequestParam(defaultValue = "0.5") Double radius
    ){

        return foodPostService.findNearbyFoodPosts(lat,lng,radius);
    }
}
