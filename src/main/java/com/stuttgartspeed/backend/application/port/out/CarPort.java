package com.stuttgartspeed.backend.application.port.out;

import com.stuttgartspeed.backend.application.domain.model.Car;

import java.util.List;

public interface CarPort
{
    void saveCar(Car car);
    Car findById(Long id);
    List<Car> findAll();
    void delete(Long id);
}
