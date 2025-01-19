package com.stuttgartspeed.backend.adapter.out;

import com.stuttgartspeed.backend.application.domain.model.Car;
import com.stuttgartspeed.backend.application.port.out.CarPort;
import com.stuttgartspeed.backend.adapter.in.web.CarController;
import com.stuttgartspeed.backend.adapter.out.mapper.CarRowMapper;
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
    public void saveCar(Car car)
    {
        String sql = "INSERT INTO car (mark, model, nbcv, production_year, weight, length, width, height, price, box, transmission, energie, rapport, nbPortes, nbPlaces, cylinders, image) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
    jdbc.update(sql, car.getMark(),car.getModel(),
            car.getNbcv(),car.getProduction_year(),
            car.getWeight(),car.getLength(),
            car.getWidth(),car.getHeight(),
            car.getPrice(),car.getBox(),
            car.getTransmission(),car.getEnergie(),
            car.getRapport(),car.getNbPortes(),
            car.getNbPlaces(),car.getCylinders(),
            car.getImage());
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
        String sql = "SELECT * FROM car";
        CarController.cars = (jdbc.query(sql, new CarRowMapper()));
        return CarController.cars;
    }

    @Override
    public void delete(Long id)
    {
        String sql = "DELETE FROM car WHERE id = ?";
        jdbc.update(sql,id);
    }
}
