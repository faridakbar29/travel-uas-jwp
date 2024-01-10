package com.travel.travel.rest;

import java.time.LocalDate;
import java.util.Date;
import java.util.Optional;

import org.hibernate.service.spi.ServiceException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.travel.travel.domain.Travel;
import com.travel.travel.service.TravelService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class TravelController {
    
    private final TravelService travelService;

    @GetMapping("/travels")
    public String getTravelss(Model model) {
        model.addAttribute("travels", travelService.getTravelss());
        return "travels";
    }

    @GetMapping("/signup-travel")
    public String showSignupForm(Travel travel) {
        return "add-travel";
    }

    @PostMapping("/travels")
    public String addTravelss(@Valid Travel travel, BindingResult bindingResult, Model model) {
        String travelId = travel.getTravelId();

        boolean exists = travelService.findTravelByTravelId(travelId).isPresent();

        if (exists) {
            throw new IllegalArgumentException("travel with id:" + travelId + "is already exist");
        }

        travelService.save(travel);

        model.addAttribute("travels", travelService.getTravelss());
        return "add-travel";
    }

    public LocalDate convertToLocalDateViaSqlDate(Date dateToConvert) {
        return new java.sql.Date(dateToConvert.getTime()).toLocalDate();
    }

    @GetMapping(value = "/travels/{travelId}")
    public ResponseEntity<Travel> findTravel(@PathVariable("travelId") String travelId) {
        Optional<Travel> travelOptional = travelService.findTravelByTravelId(travelId);
        if (travelOptional.isPresent()) {
            return new ResponseEntity<>(travelOptional.get(), HttpStatus.OK);
        } else {
            return null;
        }
    }

    @PostMapping(value = "/travels/{travelId}")
    public String updateTravel(@PathVariable("travelId") String travelId,
            Travel travel,
            BindingResult result, Model model) {

        final Optional<Travel> optionalTravel = travelService.findTravelByTravelId(travel.getTravelId());
        if (optionalTravel.isEmpty()) {
            throw new ServiceException("travel with id:" + travelId + "is not exists");
        }

        travelService.update(travel);

        model.addAttribute("travels", travelService.getTravelss());
        return "redirect:/travels";
    }

    @GetMapping("/travels/edit/{travelId}")
    public String showUpdateForm(@PathVariable("travelId") String travelId, Model model) {
        final Optional<Travel> optionalTravel = travelService.findTravelByTravelId(travelId);
        if (optionalTravel.isEmpty()) {
            throw new ServiceException("travel with id:" + travelId + "is not exists");
        }
        final Travel travelToBeUpdated = optionalTravel.get();

        model.addAttribute("travel", travelToBeUpdated);
        return "update-travel";
    }

    @GetMapping(value = "/travels/{travelId}/delete")
    public String deleteTravel(@PathVariable("travelId") String travelId) {
        travelService.delete(travelId);
        return "redirect:/travels";
    }
}
