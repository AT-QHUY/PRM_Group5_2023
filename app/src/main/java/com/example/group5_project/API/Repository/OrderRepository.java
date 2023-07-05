package com.example.group5_project.API.Repository;

import com.example.group5_project.API.Client.AdminAPIClient;
import com.example.group5_project.API.Service.OrderService;

public class OrderRepository {

    public static OrderService getOrderService(){
        return AdminAPIClient.getClient().create(OrderService.class);
    }
}
