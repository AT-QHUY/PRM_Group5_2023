package com.example.group5_project.API.Interface;

import com.example.group5_project.Entity.Order;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface OrderService {
    String ORDERS = "order";
    @GET(ORDERS)
    Call<Order[]> getAllOrders();

    @GET(ORDERS + "/{id}")
    Call<Order> getOrder(@Path("id") Object id);

    @POST(ORDERS)
    Call<Void> createOrder(@Body CreateOrderDTO dto, @Header("Authorization") String tokenHeader);

    @PUT(ORDERS)
    Call<Order> updateOrder(@Path("id") Object id, @Body Order order);

    @DELETE(ORDERS + "/{id}")
    Call<Order> deleteOrder(@Path("id") Object id);

    public class CreateOrderDTO{
        private String address;
        private String customerName;
        private int userId;



        private HashMap<String, Integer> orderDetails;

        public CreateOrderDTO(String address, String customerName, HashMap<String, Integer> orderDetails) {
            this.address = address;
            this.customerName = customerName;
            this.userId = 0;
            this.orderDetails = orderDetails;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getCustomerName() {
            return customerName;
        }

        public void setCustomerName(String customerName) {
            this.customerName = customerName;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public HashMap<String, Integer> getOrderDetails() {
            return orderDetails;
        }

        public void setOrderDetails(HashMap<String, Integer> orderDetails) {
            this.orderDetails = orderDetails;
        }
    }

}
