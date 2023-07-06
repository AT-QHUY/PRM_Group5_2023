package com.example.group5_project.API.Interface;

import com.example.group5_project.Entity.OrderDetail;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface OrderDetailService {
    String ORDERDETAILS = "orderDetail";
    @GET(ORDERDETAILS)
    Call<OrderDetail[]> getAllOrderDetails();

    @GET(ORDERDETAILS + "/{id}")
    Call<OrderDetail> getOrderDetail(@Path("id") Object id);

    @POST(ORDERDETAILS)
    Call<OrderDetail> createOrderDetail(@Body OrderDetail orderDetail);

    @PUT(ORDERDETAILS)
    Call<OrderDetail> updateOrderDetail(@Path("id") Object id, @Body OrderDetail orderDetail);

    @DELETE(ORDERDETAILS + "/{id}")
    Call<OrderDetail> deleteOrderDetail(@Path("id") Object id);
    //thuong khi khong ai delete, update order bao h
}
