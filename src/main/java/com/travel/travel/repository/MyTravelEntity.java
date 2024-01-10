package com.travel.travel.repository;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "mytravel")
@Getter
@Setter
public class MyTravelEntity {
    
      @Id 
     @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "travel_Id", nullable = false)
    private String travelId;

    @Column(name = "travel_name", nullable = false)
    private String travelName;

    @Column(name = "route")
    private String route;

    @Column(name = "travel_schedule")
    private Date travelSchedule;
}
