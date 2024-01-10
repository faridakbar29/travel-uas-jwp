package com.travel.travel.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.hibernate.service.spi.ServiceException;
import org.springframework.stereotype.Service;

import com.travel.travel.domain.MyTravel;
import com.travel.travel.repository.MyTravelEntity;
import com.travel.travel.repository.MyTravelEntityRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class MyTravelServiceImpl implements MyTravelService {
    
    private final MyTravelEntityRepository mytravelEntityRepository;

    private MyTravel map(MyTravelEntity entity) {
        final MyTravel mytravel = new MyTravel();
        mytravel.setTravelId(entity.getTravelId());
        mytravel.setTravelName(entity.getTravelName());
        mytravel.setRoute(entity.getRoute());
        mytravel.setTravelSchedule(entity.getTravelSchedule());
        return mytravel;

    }

    private MyTravelEntity map(MyTravel mytravel) {
        final MyTravelEntity entity = new MyTravelEntity();
        entity.setTravelId(mytravel.getTravelId());
        entity.setTravelName(mytravel.getTravelName());
        entity.setRoute(mytravel.getRoute());
        entity.setTravelSchedule(mytravel.getTravelSchedule());
        return entity;

    }
    
    
    @Override
    public List<MyTravel> getMyTravelss() {
        final List<MyTravelEntity> entities = mytravelEntityRepository.findAll();
        return entities.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<MyTravel> findMyTravelByTravelId(String travelId) {
        Optional<MyTravelEntity> optionalMyTravelEntity = mytravelEntityRepository.findByTravelId(travelId);
        if (optionalMyTravelEntity.isPresent()) {
            return Optional.of(this.map(optionalMyTravelEntity.get()));
        } else {
            return Optional.empty();

        }
    }

    @Override
    public void save(MyTravel myTravel) {
        mytravelEntityRepository.save(this.map(myTravel));
    }

    @Override
    public void delete(String travelId) {
       Optional<MyTravelEntity> optionalEntity = mytravelEntityRepository.findByTravelId(travelId);
        if (optionalEntity.isPresent()) {
            mytravelEntityRepository.delete(optionalEntity.get());
        } else {
            throw new ServiceException("travel with travel ID {0} not found" + travelId);
        }
    }

    @Override
    public void update(MyTravel myTravel) {
        Optional<MyTravelEntity> optionalEntity = mytravelEntityRepository.findByTravelId(myTravel.getTravelId());
    if (optionalEntity.isPresent()) {
        
        MyTravelEntity existingEntity = optionalEntity.get();
        existingEntity.setTravelName(myTravel.getTravelName());
        existingEntity.setRoute(myTravel.getRoute());
        existingEntity.setTravelSchedule(myTravel.getTravelSchedule());

        
        mytravelEntityRepository.save(existingEntity);
    } else {
        throw new ServiceException("travel with id " + myTravel.getTravelId() + " not found");
    }
    }
}