package com.stuttgartspeed.backend.web;

import com.stuttgartspeed.backend.model.Car;
import com.stuttgartspeed.backend.service.CarUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/stuttgarspeed")
@RequiredArgsConstructor
public class CarController
{
    public static List<Car> cars = new ArrayList<>();
    private final CarUseCase carUseCase;

    @GetMapping("/cars")
    public ResponseEntity<List<CarResponse>> getAllCars()
    {
        carUseCase.findAll();
        List<CarResponse> listsCars = cars.stream()
                .map(car -> CarResponse.fromEntity(car))
                .collect(toList());

        if (cars.isEmpty())
        {
            throw new ResourceNotFoundException("No cars available.");
        }

        return ResponseEntity.status(HttpStatus.OK).body(listsCars);
    }

    @GetMapping("/car")
    public ResponseEntity<CarResponse> getOneCar(@RequestParam("id") long id)
    {
        Car car = carUseCase.findById(id);

        if (car == null)
        {
            throw new ResourceNotFoundException("Car with ID " + id + " not found.");
        }

        CarResponse carResponse = CarResponse.fromEntity(car);
        return ResponseEntity.status(HttpStatus.OK).body(carResponse);
    }

    @PostMapping("/car")
    public ResponseEntity<Car> addCar(@Valid @RequestBody CarRequest carRequest)
    {
        Car car = carRequest.toEntity();



        carUseCase.save(car);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> removeCar(@PathVariable long id)
    {
        cars.remove(id-1);
        Car car = carUseCase.findById(id);


        if (car == null)
        {
            throw new ResourceNotFoundException("Car with ID " + id + " not found.");
        }

        carUseCase.delete(car);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
