package com.sportstore.service;

import com.sportstore.dao.OrderDao;
import com.sportstore.entity.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderDao orderDao;

    public OrderDao getOrderDao() {
        return orderDao;
    }

    public void setOrderDao(OrderDao orderDao) {
        this.orderDao = orderDao;
    }

    public long createOrder(Order order) {
        return orderDao.createOrder(order);
    }

    public void deleteOrder(Long orderId) {
        orderDao.deleteOrder(orderId);
    }

    public Order findOne(Long orderId) {
        return orderDao.findOne(orderId);
    }

    public long submitOrder(String customer, String amount) {
        Order order = new Order();
        order.setId(0);
        order.setCustomer(customer);
        order.setAmount(amount);
        return orderDao.createOrder(order);
    }
}
