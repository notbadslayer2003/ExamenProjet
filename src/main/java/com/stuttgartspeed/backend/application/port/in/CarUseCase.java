package com.stuttgartspeed.backend.application.port.in;

import com.stuttgartspeed.backend.application.domain.model.Car;
import java.util.List;

public interface CarUseCase
{
    Car findById(Long id);
    List<Car> findAll();
    void save(Car car);
    void delete(Car car);
}
