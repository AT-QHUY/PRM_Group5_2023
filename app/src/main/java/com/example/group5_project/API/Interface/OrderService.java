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
    Call<GetOrderDTO[]> getAllOrders(@Header("Authorization") String tokenHeader);

    @GET(ORDERS + "/{id}")
    Call<GetOrderDTO> getOrder(@Path("id") Object id,@Header("Authorization") String tokenHeader);

    @POST(ORDERS)
    Call<Void> createOrder(@Body CreateOrderDTO dto, @Header("Authorization") String tokenHeader);

    @PUT(ORDERS)
    Call<Order> updateOrder(@Path("id") Object id, @Body Order order);

    @PUT(ORDERS + "/{id}")
    Call<Void> updateOrderStatus(@Path("id") Object id, @Body UpdateOrderStatusDTO dto, @Header("Authorization") String tokenHeader);

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

    public class GetOrderDTO{
        private long totalPrice;
        private int id, status, userId;
        private String address, customerName, createDate;
        private List<GetOrderDetailDTO> orderDetails;
        public GetOrderDTO(long totalPrice, int id, int status, int userId, String address, String customerName, String createDate) {
            this.totalPrice = totalPrice;
            this.id = id;
            this.status = status;
            this.userId = userId;
            this.address = address;
            this.customerName = customerName;
            this.createDate = createDate;
        }

        public GetOrderDTO(long totalPrice, int id, int status, int userId, String address, String customerName, String createDate, List<GetOrderDetailDTO> dto_list) {
            this.totalPrice = totalPrice;
            this.id = id;
            this.status = status;
            this.userId = userId;
            this.address = address;
            this.customerName = customerName;
            this.createDate = createDate;
            this.orderDetails = dto_list;
        }

        public List<GetOrderDetailDTO> getDto_list() {
            return orderDetails;
        }

        public void setDto_list(List<GetOrderDetailDTO> dto_list) {
            this.orderDetails = dto_list;
        }

        public long getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(long totalPrice) {
            this.totalPrice = totalPrice;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
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

        public String getCreateDate() {
            return createDate;
        }

        public void setCreateDate(String createDate) {
            this.createDate = createDate;
        }
    }

    public class GetOrderDetailDTO{
        private int orderId, phoneId, quantity, price, id;

        public GetOrderDetailDTO(int orderId, int phoneId, int quantity, int price, int id) {
            this.orderId = orderId;
            this.phoneId = phoneId;
            this.quantity = quantity;
            this.price = price;
            this.id = id;
        }

        public int getOrderId() {
            return orderId;
        }

        public void setOrderId(int orderId) {
            this.orderId = orderId;
        }

        public int getPhoneId() {
            return phoneId;
        }

        public void setPhoneId(int phoneId) {
            this.phoneId = phoneId;
        }

        public int getQuantity() {
            return quantity;
        }

        public void setQuantity(int quantity) {
            this.quantity = quantity;
        }

        public int getPrice() {
            return price;
        }

        public void setPrice(int price) {
            this.price = price;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }

    public class UpdateOrderStatusDTO{
        private int status;

        public UpdateOrderStatusDTO(int status) {
            this.status = status;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }


}
