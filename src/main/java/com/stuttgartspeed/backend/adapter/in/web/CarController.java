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

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/api/cars")
@Tag(name = "Car Controller", description = "Controller pour CRUD les véhicules")
@RequiredArgsConstructor
@CrossOrigin(origins = "https://stuttgartspeed-fe.onrender.com")
public class CarController {

    private final CarUseCase carUseCase;

    @GetMapping
    public ResponseEntity<List<CarResponse>> getAllCars() {
        List<Car> cars = carUseCase.findAll();

        if (cars == null || cars.isEmpty()) {
            throw new ResourceNotFoundException("No cars available.");
        }

        List<CarResponse> listCars = cars.stream()
                .map(CarResponse::fromEntity)
                .collect(toList());

        return ResponseEntity.ok(listCars);
    }

    @GetMapping("/car")
    @Secured("ROLE_USER")
    public ResponseEntity<CarResponse> getOneCar(@RequestParam("id") long id) {
        Car car = carUseCase.findById(id);
        if (car == null) {
            throw new ResourceNotFoundException("Car with ID " + id + " not found.");
        }
        return ResponseEntity.ok(CarResponse.fromEntity(car));
    }

    @PostMapping("/car")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Void> addCar(@Valid @RequestBody CarRequest carRequest) {
        Car car = carRequest.toEntity();
        carUseCase.save(car);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @DeleteMapping("/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<Void> removeCar(@PathVariable long id) {
        Car car = carUseCase.findById(id);
        if (car == null) {
            throw new ResourceNotFoundException("Car with ID " + id + " not found.");
        }
        carUseCase.delete(car);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("car/{id}")
    @Secured("ROLE_ADMIN")
    public ResponseEntity<CarResponse> updateCar(@PathVariable long id, @Valid @RequestBody CarRequest carRequest) {
        Car existingCar = carUseCase.findById(id);
        if (existingCar == null) {
            throw new ResourceNotFoundException("Car with ID " + id + " not found.");
        }

        // Mise à jour des champs
        existingCar.setMark(carRequest.getMark());
        existingCar.setModel(carRequest.getModel());
        existingCar.setPrice(carRequest.getPrice());
        existingCar.setImage(carRequest.getImage());
        existingCar.setNbcv(carRequest.getNbcv());
        existingCar.setEnergie(carRequest.getEnergie());
        existingCar.setBox(carRequest.getBox());
        existingCar.setTransmission(carRequest.getTransmission());
        existingCar.setProduction_year(carRequest.getProduction_year());
        existingCar.setWeight(carRequest.getWeight());
        existingCar.setLength(carRequest.getLength());
        existingCar.setWidth(carRequest.getWidth());
        existingCar.setHeight(carRequest.getHeight());
        existingCar.setRapport(carRequest.getRapport());
        existingCar.setNbPortes(carRequest.getNbPortes());
        existingCar.setNbPlaces(carRequest.getNbPlaces());
        existingCar.setCylinders(carRequest.getCylinders());

        carUseCase.save(existingCar);

        return ResponseEntity.ok(CarResponse.fromEntity(existingCar));
    }
}
