package com.hungerbridge.hungerbridge.Models;


import com.hungerbridge.hungerbridge.Enums.FoodStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Builder
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "food_post")
public class FoodPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Food name is Required")  // Make sure ( Not null + Not Empty String + Not Empty Spaces only)
    @Column(nullable = false)
    private String name;

    @NotBlank(message = "Quantity is Required")
    @Column(nullable = false)
    private String quantity;

    private String photoUrl;

    @NotNull(message = "Latitude is Required")
    @Column(nullable = false)
    private Double latitude;

    @NotNull(message = "Longitude is Required")
    @Column(nullable = false)
    private Double longitude;

    @NotBlank(message = "Address is Required")
    @Column(nullable = false)
    private String address;

    @NotBlank(message = "Provider Phone Number is Required")
    @Column(nullable = false)
    private String providerPhone;


    private String providerName;


    private String notes;

    @NotNull(message = "Expiry Time is Required")
    @Column(nullable = false)
    private LocalDateTime availableUntil;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    private FoodStatus status = FoodStatus.AVAILABLE;

    @CreationTimestamp
    @Column(nullable = false)
    private LocalDateTime createdAt;



}
