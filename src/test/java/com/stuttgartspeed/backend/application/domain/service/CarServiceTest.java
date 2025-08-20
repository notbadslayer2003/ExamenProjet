package com.stuttgartspeed.backend.application.domain.service;

import com.stuttgartspeed.backend.application.domain.model.Car;
import com.stuttgartspeed.backend.application.port.out.CarPort;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;

import java.time.LocalDate;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

// Tests unitaires du service : on vérifie qu'il délègue bien au port (pas de logique métier complexe ici)
class CarServiceTest {

    private CarService service;
    private CarPort carPort;

    @BeforeEach
    void setUp() {
        carPort = mock(CarPort.class);     // port simulé (adapter DB mocké)
        service = new CarService(carPort); //le service testé dépend du port mocké
    }

    @Test
    void findById_delegates_to_port() {
        // Arrange : le port renvoie une Car attendue
        Car expected = Car.builder().id(1L).mark("Porsche").model("911").build();
        when(carPort.findById(1L)).thenReturn(expected);

        // Act : appel du service
        Car result = service.findById(1L);

        // Assert : le résultat est celui du port + le port a bien été invoqué
        assertThat(result).isEqualTo(expected);
        verify(carPort).findById(1L);
    }

    @Test
    void findAll_delegates_to_port() {
        // Arrange
        when(carPort.findAll()).thenReturn(List.of(new Car(), new Car()));

        // Act + Assert : le service renvoie la liste du port et appelle bien findAll()
        assertThat(service.findAll()).hasSize(2);
        verify(carPort).findAll();
    }

    @Test
    void save_delegates_to_port() {
        // Arrange : entité à sauvegarder
        Car car = Car.builder().mark("BMW").model("M3").build();

        // Act : appel du service (doit déléguer au port.saveCar)
        service.save(car);

        // Assert : on capture l'argument passé au port et on vérifie qu'il s'agit de la même instance
        ArgumentCaptor<Car> captor = ArgumentCaptor.forClass(Car.class);
        verify(carPort).saveCar(captor.capture());
        assertThat(captor.getValue()).isEqualTo(car);
    }

    @Test
    void delete_calls_delete_with_entity_id() {
        // Arrange : entité avec un id
        Car car = Car.builder().id(7L).build();

        // Act : suppression via service
        service.delete(car);

        // Assert : le port reçoit bien l'id de l'entité à supprimer
        verify(carPort).delete(7L);
    }
}
