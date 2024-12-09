package com.stuttgartspeed.backend;

import com.stuttgartspeed.backend.application.port.out.CarPort;
import com.stuttgartspeed.backend.application.domain.service.CarService;
import com.stuttgartspeed.backend.application.port.in.CarUseCase;
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
