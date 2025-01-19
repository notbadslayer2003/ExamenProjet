package com.stuttgartspeed.backend.adapter.in.web;

import com.stuttgartspeed.backend.application.domain.model.Car;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class CarRequest
{
    @NotNull
    @NotBlank
    @NotEmpty
    private String mark;
    @NotNull
    @NotBlank
    @NotEmpty
    private String model;
    @NotNull
    @Min(value=0)
    private Integer nbcv;
    @NotNull
    @Past
    private LocalDate production_year;
    @NotNull
    @Min(value=0)
    private Double weight;
    @NotNull
    @Min(value=0)
    private Double length;
    @NotNull
    @Min(value=0)
    private Double width;
    @NotNull
    @Min(value=0)
    private Double height;
    @NotNull
    @Min(value=0)
    private Double price;
    @NotNull
    @NotBlank
    @NotEmpty
    private String box;
    @NotNull
    @NotBlank
    @NotEmpty
    private String transmission;
    @NotNull
    @NotBlank
    @NotEmpty
    private String energie;
    @NotNull
    @Min(value=0)
    private Integer rapport;
    @NotNull
    @Min(value=0)
    private Integer nbPortes;
    @NotNull
    @Min(value=0)
    private Integer nbPlaces;
    @NotNull
    @Min(value=0)
    private Double cylinders;
    @NotNull
    @NotBlank
    private String image;

    public Car toEntity()
    {
        return Car.builder().mark(mark)
                .model(model).nbcv(nbcv)
                .production_year(production_year).weight(weight)
                .length(length).width(width)
                .height(height).price(price)
                .box(box).transmission(transmission)
                .energie(energie).rapport(rapport)
                .nbPortes(nbPortes).nbPlaces(nbPlaces)
                .cylinders(cylinders).image(image).build();
    }
}
