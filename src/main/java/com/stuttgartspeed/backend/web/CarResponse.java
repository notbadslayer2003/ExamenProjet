package com.stuttgartspeed.backend.web;

import com.stuttgartspeed.backend.model.Car;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonInclude.Include.NON_NULL;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@JsonInclude(NON_NULL)
public class CarResponse
{
    private Long id;
    private String mark;
    private String model;
    private Integer nbcv;
    private LocalDate year;
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

    public static CarResponse fromEntity(Car car)
    {
        return CarResponse.builder().id(car.getId())
                .mark(car.getMark())
                .model(car.getModel())
                .nbcv(car.getNbcv())
                .year(car.getYear())
                .weight(car.getWeight())
                .length(car.getLength())
                .width(car.getWidth())
                .height(car.getHeight())
                .price(car.getPrice())
                .box(car.getBox())
                .transmission(car.getTransmission())
                .energie(car.getEnergie())
                .rapport(car.getRapport())
                .nbPortes(car.getNbPortes())
                .nbPlaces(car.getNbPlaces())
                .cylinders(car.getCylinders()).build();
    }
}
