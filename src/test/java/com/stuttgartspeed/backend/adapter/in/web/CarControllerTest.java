package com.stuttgartspeed.backend.adapter.in.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stuttgartspeed.backend.application.domain.model.Car;
import com.stuttgartspeed.backend.application.port.in.CarUseCase;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(controllers = CarController.class)
@AutoConfigureMockMvc(addFilters = false)
class CarControllerTest {

    @Autowired MockMvc mvc;
    @Autowired ObjectMapper om;

    @MockBean CarUseCase carUseCase;

    //helper pour créer un Car complète
    private Car sample(long id) {
        return Car.builder()
                .id(id)
                .mark("Porsche")
                .model("911")
                .nbcv(400)
                .production_year(LocalDate.now().minusYears(1))
                .weight(1500.0).length(4.5).width(1.9).height(1.3)
                .price(120000.0).box("Manuelle").transmission("Propulsion")
                .energie("Essence").rapport(7).nbPortes(2).nbPlaces(4)
                .cylinders(3.0).image("img")
                .build();
    }

    //1 : crée une liste avec 2 voitures
    //2 : je mocke un appel a l'endpoint /api/cars
    //3 : j'attends un statut isOk -> statut 200 - j'attend suassi une liste à l'indice 0 (marque) porsche et a l'indice 1 (id) 2
    @Test
    void getAllCars_returns_200_and_list() throws Exception {
        when(carUseCase.findAll()).thenReturn(List.of(sample(1), sample(2)));

        mvc.perform(get("/api/cars"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].mark").value("Porsche"))
                .andExpect(jsonPath("$[1].id").value(2));
    }

    //crée une liste vide
    //2 : je mocke un appel a l'endpoint /api/cars
    //3 : j'attends un statut isNotFound -> statut 404
    @Test
    void getAllCars_returns_404_when_empty() throws Exception {
        when(carUseCase.findAll()).thenReturn(List.of());

        mvc.perform(get("/api/cars"))
                .andExpect(status().isNotFound());
    }

    //Quand je recherche a l'id 1 ca doit me retourner la voiture sample(1)
    //2 : je mocke un appel a l'endpoint /api/cars/car en passant comme param id à 1
    //3 : j'attends un statut isok -> statut 200
    //4 : et le model 911
    @Test
    @WithMockUser(roles = "USER")
    void getOneCar_returns_200() throws Exception {
        when(carUseCase.findById(1L)).thenReturn(sample(1));

        mvc.perform(get("/api/cars/car").param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.model").value("911"));
    }

    //Quand je recherche a l'id 9 ca doit me retourner NULL
    //2 : je mocke un appel a l'endpoint /api/cars/car en passant comme param id à 9
    //3 : j'attends un statut isNotFound -> statut 404
    @Test
    @WithMockUser(roles = "USER")
    void getOneCar_returns_404_when_null() throws Exception {
        when(carUseCase.findById(9L)).thenReturn(null);

        mvc.perform(get("/api/cars/car").param("id", "9"))
                .andExpect(status().isNotFound());
    }


    @Test
    @WithMockUser(roles = "ADMIN")
    void addCar_returns_201() throws Exception {
        CarRequest req = CarRequest.builder()
                .mark("BMW").model("M3").nbcv(420)
                .production_year(LocalDate.now().minusYears(1))
                .weight(1600.0).length(4.7).width(1.9).height(1.4)
                .price(85000.0).box("Manuelle").transmission("Propulsion")
                .energie("Essence").rapport(6).nbPortes(4).nbPlaces(5)
                .cylinders(3.0).image("img.png")
                .build();//appel fonction build() pour créer une CarRequest
        //je mocke a l'endpoint /api/cars/car
        mvc.perform(post("/api/cars/car").with(csrf())
                        .contentType(MediaType.APPLICATION_JSON) //type du contenu de ma requete est un JSON
                        .content(om.writeValueAsString(req))) //sérialise l’objet Java en JSON
                .andExpect(status().isCreated()); //verifie si la voiture est créée

        verify(carUseCase).save(any(Car.class));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateCar_returns_200() throws Exception {
        //on simule que la voiture d'id 5 existe côté use case
        when(carUseCase.findById(5L)).thenReturn(sample(5));

        // corps de requête pour la mise à jour (DTO d'entrée)
        CarRequest update = CarRequest.builder()
                .mark("Audi").model("RS4").nbcv(450)
                .production_year(LocalDate.now().minusYears(2))
                .weight(1700.0).length(4.8).width(1.9).height(1.4)
                .price(95000.0).box("Auto").transmission("Quattro")
                .energie("Essence").rapport(8).nbPortes(5).nbPlaces(5)
                .cylinders(3.0).image("img2.png")
                .build();

        // on envoie une requête HTTP PUT simulée sur /api/cars/car/5
        mvc.perform(put("/api/cars/car/{id}", 5).with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(update)))
                .andExpect(status().isOk())// on attend 200 OK
                .andExpect(jsonPath("$.mark").value("Audi"))// on attend 200 OK
                .andExpect(jsonPath("$.model").value("RS4"));

        // le contrôleur a bien délégué la persistance au use case
        verify(carUseCase).save(any(Car.class));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void updateCar_returns_404_when_not_found() throws Exception {
        // on simule que l'id 77 n'existe pas
        when(carUseCase.findById(77L)).thenReturn(null);

        CarRequest update = CarRequest.builder()
                .mark("X").model("Y").nbcv(100)
                .production_year(LocalDate.now().minusYears(1))
                .weight(1000.0).length(4.0).width(1.7).height(1.4)
                .price(10000.0).box("Auto").transmission("4x4").energie("Essence")
                .rapport(5).nbPortes(4).nbPlaces(5).cylinders(2.0).image("i")
                .build();

        // PUT sur une ressource inexistante => 404 attendu
        mvc.perform(put("/api/cars/car/{id}", 77).with(csrf())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(update)))
                .andExpect(status().isNotFound());
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void removeCar_returns_204() throws Exception {
        // on simule que l'id 2 existe
        when(carUseCase.findById(2L)).thenReturn(sample(2));

        // DELETE /api/cars/2 -> doit renvoyer 204 No Content
        mvc.perform(delete("/api/cars/2").with(csrf()))
                .andExpect(status().isNoContent());// suppression OK

        // vérifie que le use case a été appelé pour supprimer
        verify(carUseCase).delete(any(Car.class));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void removeCar_returns_404_when_not_found() throws Exception {
        // on simule que l'id 3 n'existe pas
        when(carUseCase.findById(3L)).thenReturn(null);
        // DELETE /api/cars/3 -> la ressource est absente, on attend 404
        mvc.perform(delete("/api/cars/3").with(csrf()))
                .andExpect(status().isNotFound());
    }
}
