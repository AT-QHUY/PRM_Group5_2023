package com.example.group5_project.API.Service;

import com.example.group5_project.Entity.Order;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface OrderService {
    String ORDER = "order";

    @GET(ORDER)
    Call<Order[]> getAllOrder();

    @GET(ORDER + "/{id}")
    Call<Order[]> getAllOrder(@Path("id") Object id);

    @POST(ORDER)
    Call<Order> createOrder(@Body Order order);

    @PUT(ORDER + "/{id}")
    Call<Order> updateOrder(@Path("id") Object id, @Body Order order);

    @DELETE(ORDER + "/{id}")
    Call<Order> deleteOrder(@Path("id") Object id);
}
