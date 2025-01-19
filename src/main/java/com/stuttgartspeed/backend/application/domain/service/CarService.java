package com.stuttgartspeed.backend.application.domain.service;

import com.stuttgartspeed.backend.application.domain.model.Car;
import com.stuttgartspeed.backend.application.port.out.CarPort;
import com.stuttgartspeed.backend.application.port.in.CarUseCase;

import java.util.List;

public class CarService implements CarUseCase
{
    private CarPort carPort;

    public CarService(CarPort carPort){this.carPort = carPort;}

    @Override
    public Car findById(Long id)
    {
        return carPort.findById(id);
    }

    @Override
    public List<Car> findAll()
    {
        return carPort.findAll();
    }

    @Override
    public void save(Car car)
    {
        carPort.saveCar(car);
    }

    @Override
    public void delete(Car car)
    {
        carPort.delete(car.getId());
    }
}
