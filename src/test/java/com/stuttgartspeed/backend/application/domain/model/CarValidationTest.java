package com.stuttgartspeed.backend.application.domain.model;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

class CarValidationTest {

    private static Validator validator;

    @BeforeAll
    static void init() {
        // Initialise un Validator standard (Hibernate Validator derrière)
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    // Fabrique une Car entièrement valide pour servir de base aux tests
    private Car validCar() {
        return Car.builder()
                .mark("Porsche")
                .model("911")
                .nbcv(350)
                .production_year(LocalDate.now().minusYears(1)) // passé
                .weight(1500.0)
                .length(4.5)
                .width(1.9)
                .height(1.3)
                .price(120000.0)
                .box("Manuelle")
                .transmission("Propulsion")
                .energie("Essence")
                .rapport(7)
                .nbPortes(2)
                .nbPlaces(4)
                .cylinders(3.0)
                .image("img.png")
                .build();
    }

    @Test
    void valid_car_has_no_violations() {
        // Quand on valide une Car correcte…
        Set<ConstraintViolation<Car>> v = validator.validate(validCar());
        // …il ne doit y avoir aucune violation
        assertThat(v).isEmpty();
    }

    @Test
    void mark_must_not_be_blank_and_max_size() {
        // On part d'un objet valide…
        Car c = validCar();
        // …on viole la contrainte sur 'mark' (annotée @NotNull/@Size)
        c.setMark("");
        // On valide et on s'attend à une violation ciblant le champ 'mark'
        Set<ConstraintViolation<Car>> v = validator.validate(c);
        assertThat(v).anyMatch(cv -> cv.getPropertyPath().toString().equals("mark"));
    }

    @Test
    void production_year_must_be_past_or_present() {
        // On force une date future pour violer @PastOrPresent
        Car c = validCar();
        c.setProduction_year(LocalDate.now().plusDays(1));
        Set<ConstraintViolation<Car>> v = validator.validate(c);
        // On doit trouver une violation sur 'production_year'
        assertThat(v).anyMatch(cv -> cv.getPropertyPath().toString().equals("production_year"));
    }

    @Test
    void numeric_fields_must_be_positive() {
        // On viole des contraintes @Positive / bornes
        Car c = validCar();
        c.setPrice(-1.0);  // prix doit être > 0
        c.setWeight(0.0);  // weight doit être > 0 (annotation @Positive)
        Set<ConstraintViolation<Car>> v = validator.validate(c);
        // On vérifie qu'il y a bien des violations pour ces deux champs
        assertThat(v).anyMatch(cv -> cv.getPropertyPath().toString().equals("price"));
        assertThat(v).anyMatch(cv -> cv.getPropertyPath().toString().equals("weight"));
    }
}
