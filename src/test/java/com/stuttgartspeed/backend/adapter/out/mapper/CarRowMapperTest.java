package com.stuttgartspeed.backend.adapter.out.mapper;

import com.stuttgartspeed.backend.application.domain.model.Car;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class CarRowMapperTest {

    @Test
    void mapRow_builds_domain_object() throws Exception {
        ResultSet rs = mock(ResultSet.class); // on simule un ResultSet JDBC

        // on prépare les valeurs que le mapper doit lire dans le ResultSet
        when(rs.getLong("id")).thenReturn(1L);
        when(rs.getString("mark")).thenReturn("Audi");
        when(rs.getString("model")).thenReturn("RS6");
        when(rs.getInt("nbcv")).thenReturn(600);
        when(rs.getObject(eq("production_year"), eq(LocalDate.class)))
                .thenReturn(LocalDate.of(2022, 1, 1)); // getObject(...) pour un LocalDate
        when(rs.getDouble("weight")).thenReturn(2050.0);
        when(rs.getDouble("length")).thenReturn(5.01);
        when(rs.getDouble("width")).thenReturn(1.95);
        when(rs.getDouble("height")).thenReturn(1.48);
        when(rs.getDouble("price")).thenReturn(145000.0);
        when(rs.getString("box")).thenReturn("Auto");
        when(rs.getString("transmission")).thenReturn("Quattro");
        when(rs.getString("energie")).thenReturn("Essence");
        when(rs.getInt("rapport")).thenReturn(8);
        when(rs.getInt("nbPortes")).thenReturn(5);   // noms de colonnes en camelCase
        when(rs.getInt("nbPlaces")).thenReturn(5);   // idem
        when(rs.getDouble("cylinders")).thenReturn(4.0);
        when(rs.getString("image")).thenReturn("img.png");

        CarRowMapper mapper = new CarRowMapper(); // on instancie le mapper testé
        Car car = mapper.mapRow(rs, 0);           // on mappe la ligne courante du ResultSet vers un Car

        // on vérifie que tous les champs importants ont été correctement mappés
        assertThat(car.getId()).isEqualTo(1L);
        assertThat(car.getMark()).isEqualTo("Audi");
        assertThat(car.getModel()).isEqualTo("RS6");
        assertThat(car.getNbcv()).isEqualTo(600);
        assertThat(car.getProduction_year()).isEqualTo(LocalDate.of(2022, 1, 1));
        assertThat(car.getNbPortes()).isEqualTo(5);
        assertThat(car.getNbPlaces()).isEqualTo(5);
    }
}
