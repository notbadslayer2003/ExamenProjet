package com.stuttgartspeed.backend.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Car
{
    private Long id;
    private String mark;
    private String model;
    private Integer nbcv;
    private LocalDate production_year ;
    private Double weight;
    private Double length;
    private Double width;
    private Double height;
    private Double price;
    private String box;
    private String transmission;
    private String energie;
    private Integer rapport;
    private Integer nbPortes;
    private Integer nbPlaces;
    private Double cylinders;
    private String image;
}
