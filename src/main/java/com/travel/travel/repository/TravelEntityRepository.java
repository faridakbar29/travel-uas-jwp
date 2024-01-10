package com.travel.travel.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TravelEntityRepository extends JpaRepository<TravelEntity, Long> {
    
    Optional<TravelEntity> findByTravelId(final String travelId);
}
