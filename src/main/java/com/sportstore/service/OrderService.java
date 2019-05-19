package com.sportstore.service;

import com.sportstore.entity.Order;

public interface OrderService {

    public long createOrder(Order order);
    public void deleteOrder(Long orderId);
    public Order findOne(Long orderId);
    public long submitOrder(String customer, String amount);
}
