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
        return new Car(rs.getLong("ID"),
                rs.getString("MARK"),
                rs.getString("MODEL"),
                rs.getInt("NBCV"),
                rs.getObject("PRODUCTION_YEAR", LocalDate.class),
                rs.getDouble("WEIGHT"),
                rs.getDouble("LENGTH"),
                rs.getDouble("WIDTH"),
                rs.getDouble("HEIGHT"),
                rs.getDouble("PRICE"),
                rs.getString("BOX"),
                rs.getString("TRANSMISSION"),
                rs.getString("ENERGIE"),
                rs.getInt("RAPPORT"),
                rs.getInt("NB_PORTES"),
                rs.getInt("NB_PLACES"),
                rs.getDouble("CYLINDERS"));
    }
}

