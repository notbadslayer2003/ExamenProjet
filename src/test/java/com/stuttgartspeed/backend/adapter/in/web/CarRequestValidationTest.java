package com.stuttgartspeed.backend.adapter.in.web;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;

// Tests unitaires de validation sur le DTO d'entrée CarRequest (annotations Jakarta Bean Validation)
class CarRequestValidationTest {

    private static Validator validator;

    @BeforeAll
    static void init() {
        // Initialise un Validator standard (Hibernate Validator sous le capot)
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    // Fabrique une requête 100% valide pour servir de base dans les tests
    private CarRequest validReq() {
        return CarRequest.builder()
                .mark("Porsche").model("911").nbcv(400)
                .production_year(LocalDate.now().minusYears(1))
                .weight(1500.0).length(4.5).width(1.9).height(1.3)
                .price(120000.0).box("Manuelle").transmission("Propulsion")
                .energie("Essence").rapport(7).nbPortes(2).nbPlaces(4)
                .cylinders(3.0).image("img.png")
                .build();
    }

    @Test
    void valid_request_has_no_violations() {
        // Quand on valide un CarRequest correct…
        Set<ConstraintViolation<CarRequest>> v = validator.validate(validReq());
        // …il ne doit y avoir aucune violation
        assertThat(v).isEmpty();
    }

    @Test
    void blank_fields_fail_validation() {
        // On part d'un request valide…
        CarRequest r = validReq();
        // …on force un champ obligatoire à vide (mark a @NotNull/@NotBlank/@NotEmpty)
        r.setMark("");
        // On valide l'objet
        Set<ConstraintViolation<CarRequest>> v = validator.validate(r);
        // On s'attend à au moins une violation visant la propriété "mark"
        assertThat(v).anyMatch(cv -> cv.getPropertyPath().toString().equals("mark"));
    }

    @Test
    void production_year_must_be_past() {
        // On part d'un request valide…
        CarRequest r = validReq();
        // …et on met une date future (CarRequest a @Past sur production_year)
        r.setProduction_year(LocalDate.now().plusDays(1));
        // On valide l'objet
        Set<ConstraintViolation<CarRequest>> v = validator.validate(r);
        // On s'attend à au moins une violation sur "production_year"
        assertThat(v).anyMatch(cv -> cv.getPropertyPath().toString().equals("production_year"));
    }
}
