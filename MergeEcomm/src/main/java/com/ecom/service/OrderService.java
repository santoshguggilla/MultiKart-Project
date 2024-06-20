package com.ecom.service;

import java.util.List; 
import com.ecom.beans.Order;

public interface OrderService {
	
	Order addOrderName(Order order);
	public String getRandomNumberString();
	
 

	List<Order> getAllOrderDetails();
    public Order orderId(int id);
    


	


 

 



	

}
