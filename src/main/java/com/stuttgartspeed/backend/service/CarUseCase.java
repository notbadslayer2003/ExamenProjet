package com.stuttgartspeed.backend.service;

import com.stuttgartspeed.backend.model.Car;
import java.util.List;

public interface CarUseCase
{
    Car findById(Long id);
    List<Car> findAll();
    void save(Car car);
    void delete(Car car);
}
