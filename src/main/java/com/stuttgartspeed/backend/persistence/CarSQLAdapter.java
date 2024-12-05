package com.stuttgartspeed.backend.persistence;

import com.stuttgartspeed.backend.model.Car;
import com.stuttgartspeed.backend.service.CarPort;
import com.stuttgartspeed.backend.web.CarController;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CarSQLAdapter implements CarPort
{
    private JdbcTemplate jdbc;
    public CarSQLAdapter(JdbcTemplate jdbc)
    {
        this.jdbc = jdbc;
    }

    @Override
    public void saveCar(Car car) {
        String sql = "INSERT INTO car (mark, model, nbcv, production_year, weight, length, width, height, price, box, transmission, energie, rapport, \"NB_PORTES\", \"NB_PLACES\", cylinders) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        jdbc.update(sql, car.getMark(), car.getModel(),
                car.getNbcv(), car.getProduction_year(),
                car.getWeight(), car.getLength(),
                car.getWidth(), car.getHeight(),
                car.getPrice(), car.getBox(),
                car.getTransmission(), car.getEnergie(),
                car.getRapport(), car.getNbPortes(),
                car.getNbPlaces(), car.getCylinders());
    }


    @Override
    public Car findById(Long id)
    {
        String sql = "SELECT * FROM car WHERE id = ?";
        return jdbc.queryForObject(sql, new CarRowMapper(), id);
    }

    @Override
    public List<Car> findAll()
    {
        String sql = "SELECT ID, MARK, MODEL, NBCV, PRODUCTION_YEAR, WEIGHT, LENGTH, WIDTH, HEIGHT, PRICE, BOX, TRANSMISSION, ENERGIE, RAPPORT, NB_PORTES, NB_PLACES, CYLINDERS FROM CAR";
        return jdbc.query(sql, new CarRowMapper());
    }

    @Override
    public void delete(Long id)
    {
        String sql = "DELETE FROM car WHERE id = ?";
        jdbc.update(sql,id);
    }
}
