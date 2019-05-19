package com.sportstore.dao;

import com.sportstore.entity.Shoes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

@Repository
public class ShoesDaoImpl implements ShoesDao {
	@Autowired
	private JdbcTemplate jdbcTemplate;

    private static final class ShoesMapper implements RowMapper<Shoes> {
        public Shoes mapRow(ResultSet rs, int rowNum) throws SQLException {
            Shoes shoes = new Shoes();
            shoes.setId(rs.getLong("id"));
            shoes.setShoesname(rs.getString("shoesname"));
            shoes.setPrice(rs.getString("price"));
            shoes.setQuantity(rs.getInt("quantity"));
            shoes.setIsvip(rs.getBoolean("isvip"));
            shoes.setDescription(rs.getString("description"));
            return shoes;
        }
    }
	
    public Shoes createShoes(final Shoes shoes) {
        final String sql = "insert into sys_shoes(shoesname, price, quantity, isvip, description) values(?,?,?,?,?)";

        GeneratedKeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(new PreparedStatementCreator() {
            public PreparedStatement createPreparedStatement(Connection connection) throws SQLException {
                PreparedStatement psst = connection.prepareStatement(sql, new String[]{"id"});
                psst.setString(1, shoes.getShoesname());
                psst.setString(2, shoes.getPrice());
                psst.setInt(3, shoes.getQuantity());
                psst.setBoolean(4, shoes.getIsvip());
                psst.setString(5, shoes.getDescription());
                return psst;
            }
        }, keyHolder);

        shoes.setId(keyHolder.getKey().longValue());
        return shoes;
    }

    public Shoes updateShoes(Shoes shoes) {
        String sql = "update sys_shoes set shoesname=?, price=?, quantity=?, isvip=?, description=? where id=?";
        jdbcTemplate.update(sql, shoes.getShoesname(), shoes.getPrice(), shoes.getQuantity(), shoes.getIsvip(), shoes.getDescription(), shoes.getId());
        return shoes;
    }

    public void deleteShoes(Long shoesId) {
        String sql = "delete from sys_shoes where id=?";
        jdbcTemplate.update(sql, shoesId);
    }

    public Shoes findOne(Long shoesId) {
        String sql = "select * from sys_shoes where id=?";
        return jdbcTemplate.queryForObject(sql, new Object[]{shoesId}, new ShoesMapper());
    }

    public List<Shoes> findAll() {
        String sql = "select * from sys_shoes";
        return jdbcTemplate.query(sql, new ShoesMapper());
    }
}
