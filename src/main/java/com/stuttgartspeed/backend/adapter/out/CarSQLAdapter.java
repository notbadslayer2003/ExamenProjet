package com.stuttgartspeed.backend.adapter.out;

import com.stuttgartspeed.backend.adapter.out.mapper.CarRowMapper;
import com.stuttgartspeed.backend.application.domain.model.Car;
import com.stuttgartspeed.backend.application.port.out.CarPort;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CarSQLAdapter implements CarPort {

    private final JdbcTemplate jdbc;

    public CarSQLAdapter(JdbcTemplate jdbc) {
        this.jdbc = jdbc;
    }

    @Override
    public List<Car> findAll() {
        // évite toute référence au controller
        String sql = """
            SELECT id, mark, model, nbcv, production_year, weight, length, width, height,
                   price, box, transmission, energie, rapport, nbPortes, nbPlaces, cylinders, image
            FROM car
        """;
        return jdbc.query(sql, new CarRowMapper());
    }

    @Override
    public Car findById(Long id) {
        String sql = """
            SELECT id, mark, model, nbcv, production_year, weight, length, width, height,
                   price, box, transmission, energie, rapport, nbPortes, nbPlaces, cylinders, image
            FROM car
            WHERE id = ?
        """;
        List<Car> rows = jdbc.query(sql, new CarRowMapper(), id);
        return rows.isEmpty() ? null : rows.get(0);
    }

    @Override
    public void saveCar(Car c) {
        if (c.getId() == null) {
            // INSERT
            String sql = """
                INSERT INTO car (mark, model, nbcv, production_year, weight, length, width, height,
                                 price, box, transmission, energie, rapport, nbPortes, nbPlaces, cylinders, image)
                VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
            """;
            jdbc.update(sql,
                    c.getMark(), c.getModel(), c.getNbcv(), c.getProduction_year(),
                    c.getWeight(), c.getLength(), c.getWidth(), c.getHeight(),
                    c.getPrice(), c.getBox(), c.getTransmission(), c.getEnergie(),
                    c.getRapport(), c.getNbPortes(), c.getNbPlaces(), c.getCylinders(), c.getImage()
            );
        } else {
            // UPDATE
            String sql = """
                UPDATE car SET mark=?, model=?, nbcv=?, production_year=?, weight=?, length=?, width=?, height=?,
                               price=?, box=?, transmission=?, energie=?, rapport=?, nbPortes=?, nbPlaces=?, cylinders=?, image=?
                WHERE id=?
            """;
            jdbc.update(sql,
                    c.getMark(), c.getModel(), c.getNbcv(), c.getProduction_year(),
                    c.getWeight(), c.getLength(), c.getWidth(), c.getHeight(),
                    c.getPrice(), c.getBox(), c.getTransmission(), c.getEnergie(),
                    c.getRapport(), c.getNbPortes(), c.getNbPlaces(), c.getCylinders(), c.getImage(),
                    c.getId()
            );
        }
    }

    @Override
    public void delete(Long id) {
        jdbc.update("DELETE FROM car WHERE id = ?", id);
    }
}
