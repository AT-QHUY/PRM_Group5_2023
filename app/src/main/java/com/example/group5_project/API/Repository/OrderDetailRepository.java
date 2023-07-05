package com.example.group5_project.API.Repository;

import com.example.group5_project.API.Client.APIClient;
import com.example.group5_project.API.Interface.OrderDetailService;

public class OrderDetailRepository {
    public static OrderDetailService getOrderDetailService(){
        return APIClient.getClient().create(OrderDetailService.class);
    }
}
