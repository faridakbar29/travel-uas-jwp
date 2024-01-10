package com.travel.travel.service;

import java.util.List;
import java.util.Optional;

import com.travel.travel.domain.Travel;

public interface TravelService {
    
    List<Travel> getTravelss();

    Optional<Travel> findTravelByTravelId(final String travelId);

    void save(Travel travel);

    void delete(final String travelId);

    void update(final Travel travel);
}
