package com.travel.travel.service;

import java.util.List;
import java.util.Optional;

import com.travel.travel.domain.MyTravel;


public interface MyTravelService {
    
    List<MyTravel> getMyTravelss();

    Optional<MyTravel> findMyTravelByTravelId(final String travelId);

    void save(MyTravel myTravel);

    void delete(final String travelId);

    void update(final MyTravel myTravel);
}
