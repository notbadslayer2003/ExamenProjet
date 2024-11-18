package com.stuttgartspeed.backend;

import com.stuttgartspeed.backend.service.CarPort;
import com.stuttgartspeed.backend.service.CarService;
import com.stuttgartspeed.backend.service.CarUseCase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DemoConfiguration
{
    @Autowired
    private CarPort carPort;

    @Bean public CarUseCase carUseCase()
    {
        CarUseCase carUseCase = new CarService(carPort);
        return carUseCase;
    }
}
