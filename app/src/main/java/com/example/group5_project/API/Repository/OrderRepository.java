package com.example.group5_project.API.Repository;

import com.example.group5_project.API.Client.APIClient;
import com.example.group5_project.API.Interface.OrderService;

public class OrderRepository {
    public static OrderService getOrderService(){
        return APIClient.getClient().create(OrderService.class);
    }
}
