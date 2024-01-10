package com.travel.travel.domain;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public class Travel {
    
    private String travelId;

    @NotBlank(message = "full name is required")
    @Size(min = 3, max = 50)
    private String travelName;

    private String route;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @NotNull(message = "Practice Schedule is required")
    private Date travelSchedule;

    public  Travel() {
    }

    public String getTravelName() {
        return travelName;
    }

    public void setTravelName(String travelName) {
        this.travelName = travelName;
    }

    public String getRoute() {
        return route;
    }

    public void setRoute(String route) {
        this.route = route;
    }


    public String getTravelId() {
        return travelId;
    }

    public void setTravelId(String travelId) {
        this.travelId = travelId;
    }

    public Date getTravelSchedule() {
        return travelSchedule;
    }

    public void setTravelSchedule(Date travelSchedule) {
        this.travelSchedule = travelSchedule;
    }
}
