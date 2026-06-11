package com.hungerbridge.hungerbridge.Models;


import com.hungerbridge.hungerbridge.Enums.ClaimStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "volunteer_claim")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class VolunteerClaim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "food_post_id",nullable = false)
    private FoodPost foodPost;

    @NotBlank(message = "Volunteer name is Required")
    @Column(nullable = false)
    private String volunteerName;

    @NotBlank(message = "Volunteer phone is Required")
    @Column(nullable = false)
    private String volunteerPhone;


    private String otp;


    private boolean otpVerified;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ClaimStatus status = ClaimStatus.CLAIMED;

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime claimedAt;

    private LocalDateTime pickedUpAt;

    private LocalDateTime deliveredAt;
}
