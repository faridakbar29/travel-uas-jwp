package com.travel.travel.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface MyTravelEntityRepository extends  JpaRepository<MyTravelEntity, Long> {
    
    Optional<MyTravelEntity> findByTravelId(final String travelId);
}