package com.example.group5_project.API.Service;

import com.example.group5_project.Entity.Order;
import com.example.group5_project.Entity.OrderDetail;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface OrderDetailService {
    String ORDER_DETAIL = "order_detail";

    @GET(ORDER_DETAIL + "/{id}")
    Call<OrderDetail[]> getAllOrderDetailByOrderId(@Path("id") Object id);

    @POST(ORDER_DETAIL)
    Call<OrderDetail> createOrderDetail(@Body OrderDetail orderDetail);

    @PUT(ORDER_DETAIL + "/{id}")
    Call<OrderDetail> updateOrderDetail(@Path("id") Object id, @Body OrderDetail orderDetail);

    @DELETE(ORDER_DETAIL + "/{id}")
    Call<OrderDetail> deleteOrderDetail(@Path("id") Object id);
}
