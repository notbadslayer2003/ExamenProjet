package com.stuttgartspeed.backend.service;

import com.stuttgartspeed.backend.model.Car;

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
