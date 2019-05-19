package com.sportstore.service;

import com.sportstore.dao.ShoesDao;
import com.sportstore.entity.Shoes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ShoesServiceImpl implements ShoesService {
    @Autowired
    private ShoesDao shoesDao;

    public ShoesDao getShoesDao() {
        return shoesDao;
    }

    public void setShoesDao(ShoesDao shoesDao) {
        this.shoesDao = shoesDao;
    }

    @Caching(put = @CachePut(value="shoesCache", key="'shoes'+#shoes.id"),
             evict = @CacheEvict(value="shoesCache", key="'findAllShoes'"))
    public Shoes createShoes(Shoes shoes) {
        return shoesDao.createShoes(shoes);
    }


    @Caching(put = @CachePut(value="shoesCache", key="'shoes'+#shoes.id"),
             evict = @CacheEvict(value="shoesCache", key="'findAllShoes'"))
    public Shoes updateShoes(Shoes shoes) {
        return shoesDao.updateShoes(shoes);
    }

    @Caching(evict = {@CacheEvict(value="shoesCache", key="'shoes'+#shoesId"),
                      @CacheEvict(value="shoesCache", key="'findAllShoes'")})
    public void deleteShoes(Long shoesId) {
        shoesDao.deleteShoes(shoesId);
    }

    @Cacheable(value="shoesCache", key="'shoes'+#shoesId")
    public Shoes findOne(Long shoesId) {
        return shoesDao.findOne(shoesId);
    }

    @Cacheable(value="shoesCache", key="'findAllShoes'")
    public List<Shoes> findAll() {
        return shoesDao.findAll();
    }
}
