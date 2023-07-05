package com.example.group5_project.API.Repository;

import com.example.group5_project.API.Client.AdminAPIClient;
import com.example.group5_project.API.Service.OrderDetailService;

public class OrderDetailRepository {

    public static OrderDetailService getOrderDetailService(){
        return AdminAPIClient.getClient().create(OrderDetailService.class);
    }
}
