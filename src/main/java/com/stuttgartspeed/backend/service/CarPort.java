package com.stuttgartspeed.backend.service;

import com.stuttgartspeed.backend.model.Car;

import java.util.List;

public interface CarPort
{
    void saveCar(Car car);
    Car findById(Long id);
    List<Car> findAll();
}
