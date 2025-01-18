package com.stuttgartspeed.backend.adapter.in.web;

import com.stuttgartspeed.backend.application.domain.model.Car;
import com.stuttgartspeed.backend.application.port.in.CarUseCase;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;


import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api/cars")
@Tag(name = "Car Controller" ,description = "Controller pour CRUD les véhicules")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class CarController
{
    public static List<Car> cars = new ArrayList<>();
    private final CarUseCase carUseCase;

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping
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

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/car")
    @Secured("ROLE_USER")
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

    @CrossOrigin(origins = "http://localhost:5173")
    @PostMapping("/car")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Car> addCar(@Valid @RequestBody CarRequest carRequest)
    {
        Car car = carRequest.toEntity();
        carUseCase.save(car);

        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @DeleteMapping("/{id}")
    @Secured("ROLE_ADMIN")
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