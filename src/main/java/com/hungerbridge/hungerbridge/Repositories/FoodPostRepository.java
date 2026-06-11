package com.hungerbridge.hungerbridge.Repositories;

import com.hungerbridge.hungerbridge.Models.FoodPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface FoodPostRepository extends JpaRepository<FoodPost,Long> {


    @Query(value = """
            SELECT * FROM food_post f
            WHERE f.status = 'AVAILABLE'
            AND (
                6371 * ACOS(
                    COS(RADIANS(:lat)) * COS(RADIANS(f.latitude)) *
                    COS(RADIANS(f.longitude) - RADIANS(:lng)) +
                    SIN(RADIANS(:lat)) * SIN(RADIANS(f.latitude))
                )
            ) <= :radius
            ORDER BY (
                6371 * ACOS(
                    COS(RADIANS(:lat)) * COS(RADIANS(f.latitude)) *
                    COS(RADIANS(f.longitude) - RADIANS(:lng)) +
                    SIN(RADIANS(:lat)) * SIN(RADIANS(f.latitude))
                )
            ) ASC
            """, nativeQuery = true)
    List<FoodPost> findNearbyAvailablePosts(
            @Param("lat") Double lat,
            @Param("lng") Double lng,
            @Param("radius") Double radius
    );

}
