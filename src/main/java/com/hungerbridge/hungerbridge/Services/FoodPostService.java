package com.hungerbridge.hungerbridge.Services;


import com.hungerbridge.hungerbridge.Dtos.FoodPostRequest;
import com.hungerbridge.hungerbridge.Dtos.FoodPostResponse;
import com.hungerbridge.hungerbridge.Enums.FoodStatus;
import com.hungerbridge.hungerbridge.Models.FoodPost;
import com.hungerbridge.hungerbridge.Repositories.FoodPostRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FoodPostService {

    @Autowired
    private FoodPostRepository foodPostRepository;


    public FoodPostResponse createFoodPost(FoodPostRequest foodPostRequest){

        FoodPost foodPost = mapToFoodPost(foodPostRequest);

        FoodPost response = foodPostRepository.save(foodPost);

        return mapToFoodResponse(response);

    }

    public FoodPostResponse getFoodPostById(Long id){
        FoodPost foodPost = foodPostRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Food post with id "+id+" not exist"));

        return mapToFoodResponse(foodPost);
    }

    public void deleteFoodPostById(Long id){
        FoodPost foodPost = foodPostRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Food post with id "+id+" not exist"));

        if(foodPost.getStatus() == FoodStatus.CLAIMED) {
            throw new RuntimeException("Food post with id "+id+" was claimed by volunteer, cannot delete now..");
        }
        foodPostRepository.delete(foodPost);
    }

    public List<FoodPostResponse> findNearbyFoodPosts(Double lat,Double lng,Double radius){
        List<FoodPost> posts = foodPostRepository.findNearbyAvailablePosts(lat, lng, radius);

        return posts.stream()
                .map(post -> {
                    double distance = calculateDistance(lat, lng, post.getLatitude(), post.getLongitude());
                    FoodPostResponse foodPostResponse =  mapToFoodResponse(post);
                    foodPostResponse.setDistanceKm( Math.round(distance * 10.0) / 10.0);
                    return foodPostResponse;
                })
                .collect(Collectors.toList());
    }

    private double calculateDistance(double lat1, double lng1, double lat2, double lng2) {
        final int EARTH_RADIUS_KM = 6371;
        double latDistance = Math.toRadians(lat2 - lat1);
        double lngDistance = Math.toRadians(lng2 - lng1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lngDistance / 2) * Math.sin(lngDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return EARTH_RADIUS_KM * c;
    }

    private FoodPostResponse mapToFoodResponse(FoodPost response) {
        return FoodPostResponse.builder()
                .id(response.getId())
                .foodName(response.getName())
                .quantity(response.getQuantity())
                .latitude(response.getLatitude())
                .longitude(response.getLongitude())
                .photoUrl(response.getPhotoUrl())
                .status(response.getStatus())
                .createdAt(response.getCreatedAt())
                // distance need to be added
                .address(response.getAddress())
                .providerName(response.getProviderName())
                .notes(response.getNotes())
                .availableUntil(response.getAvailableUntil())
                .build();
    }


    public  FoodPost mapToFoodPost(FoodPostRequest foodPostRequest){
        return FoodPost.builder()
                .name(foodPostRequest.getFoodName())
                .quantity(foodPostRequest.getQuantity())
                .photoUrl(foodPostRequest.getPhotoUrl())
                .latitude(foodPostRequest.getLatitude())
                .longitude(foodPostRequest.getLongitude())
                .address(foodPostRequest.getAddress())
                .providerPhone(foodPostRequest.getProviderPhone())
                .providerName(foodPostRequest.getProviderName())
                .notes(foodPostRequest.getNotes())
                .availableUntil(foodPostRequest.getAvailableUntil())
                .build();
    }

}
