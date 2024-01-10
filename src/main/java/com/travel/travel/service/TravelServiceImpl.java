package com.travel.travel.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.hibernate.service.spi.ServiceException;
import org.springframework.stereotype.Service;

import com.travel.travel.domain.Travel;
import com.travel.travel.repository.TravelEntity;
import com.travel.travel.repository.TravelEntityRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TravelServiceImpl implements TravelService {
    
    private final TravelEntityRepository travelEntityRepository;

    private Travel map(TravelEntity entity) {
        final Travel travel = new Travel();
        travel.setTravelId(entity.getTravelId());
        travel.setTravelName(entity.getTravelName());
        travel.setRoute(entity.getRoute());
        travel.setTravelSchedule(entity.getTravelSchedule());
        return travel;

    }

    private TravelEntity map(Travel travel) {
        final TravelEntity entity = new TravelEntity();
        entity.setTravelId(travel.getTravelId());
        entity.setTravelName(travel.getTravelName());
        entity.setRoute(travel.getRoute());
        entity.setTravelSchedule(travel.getTravelSchedule());
        return entity;

    }
    
    
    @Override
    public List<Travel> getTravelss() {
        final List<TravelEntity> entities = travelEntityRepository.findAll();
        return entities.stream()
                .map(this::map)
                .collect(Collectors.toList());
    }

    @Override
    public Optional<Travel> findTravelByTravelId(String travelId) {
        Optional<TravelEntity> optionalTravelEntity = travelEntityRepository.findByTravelId(travelId);
        if (optionalTravelEntity.isPresent()) {
            return Optional.of(this.map(optionalTravelEntity.get()));
        } else {
            return Optional.empty();

        }
    }

    @Override
    public void save(Travel travel) {
        travelEntityRepository.save(this.map(travel));
    }

    @Override
    public void delete(String travelId) {
         Optional<TravelEntity> optionalEntity = travelEntityRepository.findByTravelId(travelId);
        if (optionalEntity.isPresent()) {
            travelEntityRepository.delete(optionalEntity.get());
        } else {
            throw new ServiceException("travel with travel ID {0} not found" + travelId);
        }
    }

    @Override
    public void update(Travel travel) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'update'");
    }
    
}
