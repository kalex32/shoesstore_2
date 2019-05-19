package com.sportstore.dao;

import com.sportstore.entity.Order;

public interface OrderDao {

    public long createOrder(Order order);
    public void deleteOrder(Long orderId);
    public Order findOne(Long orderId);
}
