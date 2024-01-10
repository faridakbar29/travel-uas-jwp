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

import com.travel.travel.domain.MyTravel;
import com.travel.travel.service.MyTravelService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@Controller
@RequiredArgsConstructor
public class MyTravelController {
    
    private final MyTravelService myTravelService;

    @GetMapping("/mytravels")
    public String getMyTravelss(Model model) {
        model.addAttribute("mytravels", myTravelService.getMyTravelss());
        return "mytravels";
    }


    @GetMapping("/signup-mytravels")
    public String showAddMyTravelsForm(Model model) {
        model.addAttribute("mytravel", new MyTravel()); // Gantilah MyTravel dengan nama kelas objek Anda
        return "add-mytravels";
    }



    @PostMapping("/mytravels")
    public String addMyTravelss(@Valid MyTravel myTravel, BindingResult bindingResult, Model model) {
        String travelId = myTravel.getTravelId();

        boolean exists = myTravelService.findMyTravelByTravelId(travelId).isPresent();

        if (exists) {
            throw new IllegalArgumentException("travel with id:" + travelId + "is already exist");
        }

        myTravelService.save(myTravel);

        model.addAttribute("mytravels", myTravelService.getMyTravelss());
        return "mytravels";
    }

    public LocalDate convertToLocalDateViaSqlDate(Date dateToConvert) {
        return new java.sql.Date(dateToConvert.getTime()).toLocalDate();
    }

    @GetMapping(value = "/mytravels/{travelId}")
    public ResponseEntity<MyTravel> findMyTravel(@PathVariable("travelId") String travelId) {
        Optional<MyTravel> mytravelOptional = myTravelService.findMyTravelByTravelId(travelId);
        if (mytravelOptional.isPresent()) {
            return new ResponseEntity<>(mytravelOptional.get(), HttpStatus.OK);
        } else {
            return null;
        }
    }

    @PostMapping(value = "/mytravels/{travelId}")
    public String updatemyTravel(@PathVariable("travelId") String travelId,
            MyTravel myTravel,
            BindingResult result, Model model) {

        final Optional<MyTravel> optionalmyTravel = myTravelService.findMyTravelByTravelId(myTravel.getTravelId());
        if (optionalmyTravel.isEmpty()) {
            throw new ServiceException("travel with id:" + travelId + "is not exists");
        }

        myTravelService.update(myTravel);

        model.addAttribute("mytravels", myTravelService.getMyTravelss());
        return "redirect:/mytravels";
    }

    @GetMapping("/mytravels/edit/{travelId}")
    public String showUpdateForm(@PathVariable("travelId") String travelId, Model model) {
        final Optional<MyTravel> optionalMyTravel = myTravelService.findMyTravelByTravelId(travelId);
        if (optionalMyTravel.isEmpty()) {
            throw new ServiceException("travel with code:" + travelId + "is not exists");
        }
        final MyTravel myTravelToBeUpdated = optionalMyTravel.get();

        model.addAttribute("mytravel", myTravelToBeUpdated);
        return "update-mytravels";
    }


    @GetMapping(value = "/mytravels/{travelId}/delete")
    public String deleteMyTravel(@PathVariable("travelId") String travelId) {
        myTravelService.delete(travelId);
        return "redirect:/mytravels";
    }
}
