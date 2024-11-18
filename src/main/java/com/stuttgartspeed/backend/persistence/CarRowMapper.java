package com.stuttgartspeed.backend.persistence;

import com.stuttgartspeed.backend.model.Car;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
public class CarRowMapper implements RowMapper<Car>
{
    @Override
    public Car mapRow(ResultSet rs, int i) throws SQLException
    {
        return new Car(rs.getLong("id"),
                rs.getString("mark"),
                rs.getString("model"),
                rs.getInt("nbcv"),
                rs.getObject("year", LocalDate.class),
                rs.getDouble("weight"),
                rs.getDouble("length"),
                rs.getDouble("width"),
                rs.getDouble("height"),
                rs.getDouble("price"),
                rs.getString("box"),
                rs.getString("transmission"),
                rs.getString("energie"),
                rs.getInt("rapport"),
                rs.getInt("nbPortes"),
                rs.getInt("nbPlaces"),
                rs.getDouble("cylinders"));
    }
}

