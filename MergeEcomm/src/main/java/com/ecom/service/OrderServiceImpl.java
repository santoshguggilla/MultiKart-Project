package com.ecom.service;

import java.util.List;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecom.beans.Order;
import com.ecom.repository.OrderRepository;
@Service
public class OrderServiceImpl  implements OrderService {

	@Autowired
	OrderRepository orderRepository;
	@Override
	public String getRandomNumberString() {
		// It will generate 6 digit random Number.
	    // from 0 to 999999
	    Random rnd = new Random();
	    int number = rnd.nextInt(999999);

	    // this will convert any number sequence into 6 character.
	    return String.format("%06d", number);
	}
	@Override
	public List<Order> getAllOrderDetails() {
		// TODO Auto-generated method stub
		return  orderRepository.findAll();
	}
	@Override
	public Order addOrderName(Order order) {
		// TODO Auto-generated method stub
		 return orderRepository.save(order);
	}
	@Override
	public Order orderId(int id) {
		return orderRepository.getById(id);
	}

}
