package com.stuttgartspeed.backend.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.domain.EntityScan;

import java.time.LocalDate;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class Car
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(min = 1, max = 50, message = "La marque doit contenir entre 1 et 50 caractères.")
    private String mark;

    @NotNull
    @Size(min = 1, max = 50, message = "Le modèle doit contenir entre 1 et 50 caractères")
    private String model;

    @NotNull
    @Min(value = 30, message = "Le nombre de chevaux doit être supérieur à 30")
    private Integer nbcv;

    @NotNull
    @PastOrPresent(message = "L'année de production doit être dans le passé ou le présent.")
    private LocalDate production_year;

    @NotNull
    @Positive(message = "Le poids doit être un nombre positif")
    private Double weight;

    @NotNull
    @Positive(message = "La longueur doit être un nombre positif")
    private Double length;

    @NotNull
    @Positive(message = "La largeur doit être un nombre positif")
    private Double width;

    @NotNull
    @Positive(message = "La hauteur doit être un nombre positif")
    private Double height;

    @NotNull
    @Positive(message = "Le prix doit être un nombre positif")
    private Double price;

    @NotNull
    @Size(min = 1, max = 12, message = "Le type de boite doit être entre 1 et 12 caractères")
    private String box;

    @NotNull
    @Size(min = 1, max = 20, message = "Le type de transmission doit etre entre 1 et 20 caractères")
    private String transmission;

    @NotNull
    @Size(min = 1, max = 20, message = "Le type de motorisation doit etre compris entre 1 et 20 caractères")
    private String energie;

    @NotNull
    @Min(value = 1, message = "Le nombre de rapports doit être supérieur ou égal à 1")
    private Integer rapport;

    @NotNull
    @Min(value = 2, message = "Le nombre de portes doit être supérieur ou égal a 2")
    private Integer nbPortes;

    @NotNull
    @Min(value = 1, message = "Le nombre de places doit être supérieur ou égal à 1")
    private Integer nbPlaces;

    @NotNull
    @Positive(message = "La cylindrée doit être un nombre positif.")
    private Double cylinders;
    private String image;
}
