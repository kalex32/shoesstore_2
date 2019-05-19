package com.sportstore.dao;

import com.sportstore.entity.Shoes;

import java.util.List;

public interface ShoesDao {

    public Shoes createShoes(Shoes shoes);
    public Shoes updateShoes(Shoes shoes);
    public void deleteShoes(Long shoesId);
    public Shoes findOne(Long shoesId);
    public List<Shoes> findAll();
}
