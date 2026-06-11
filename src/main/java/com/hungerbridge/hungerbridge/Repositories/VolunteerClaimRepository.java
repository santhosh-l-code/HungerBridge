package com.hungerbridge.hungerbridge.Repositories;

import com.hungerbridge.hungerbridge.Models.VolunteerClaim;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface VolunteerClaimRepository extends JpaRepository<VolunteerClaim,Long> {

}
