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
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api/cars")
@RequiredArgsConstructor
public class CarController
{
    public static List<Car> cars = new ArrayList<>();
    private final CarUseCase carUseCase;

    @GetMapping
    public ResponseEntity<List<CarResponse>> getAllCars()
    {
        // Récupérer les voitures directement depuis le service
        List<Car> cars = carUseCase.findAll();

        // Transformer les entités en réponses
        List<CarResponse> listsCars = cars.stream()
                .map(CarResponse::fromEntity)
                .collect(Collectors.toList());

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
