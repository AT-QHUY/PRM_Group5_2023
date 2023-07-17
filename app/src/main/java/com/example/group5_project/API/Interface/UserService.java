package com.example.group5_project.API.Interface;

import com.example.group5_project.Entity.User;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface UserService {
    String USERS = "users";
    String AUTHENTICATION = "authentication";
    @GET(USERS)
    Call<User[]> getAllUsers();

    @GET(USERS + "/{id}")
    Call<GetUserDTO> getUser(@Path("id") Object id);

    @GET(USERS + "/{keyword}")
    Call<User> searchUser(@Path("keyword") String keyword);

    @POST(USERS)
    Call<User> createUser(@Body User user);

    @PUT(USERS)
    Call<User> updateUser(@Path("id") Object id, @Body User user);

    @DELETE(USERS + "/{id}")
    Call<User> deleteUser(@Path("id") Object id);

    // AUTHENTICATION

    @POST(AUTHENTICATION + "/sign-in")
    Call<TokenDTO> login(@Body LoginDTO dto);

    public class LoginDTO{
        private String username, password;

        public LoginDTO(String username, String password) {
            this.username = username;
            this.password = password;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }
    }

    public class TokenDTO{
        private String token;

        public TokenDTO(String token) {
            this.token = token;
        }

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }
    }

    public class GetUserDTO{
        private int id;
        private String name, username, password,phoneNumber, address, role;
        private GetUserOrderListDTO orders;

        public GetUserDTO(int id, String name, String username, String password, String phoneNumber, String address, String role, GetUserOrderListDTO orders) {
            this.id = id;
            this.name = name;
            this.username = username;
            this.password = password;
            this.phoneNumber = phoneNumber;
            this.address = address;
            this.role = role;
            this.orders = orders;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getPhoneNumber() {
            return phoneNumber;
        }

        public void setPhoneNumber(String phoneNumber) {
            this.phoneNumber = phoneNumber;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getRole() {
            return role;
        }

        public void setRole(String role) {
            this.role = role;
        }

        public GetUserOrderListDTO getOrders() {
            return orders;
        }

        public void setOrders(GetUserOrderListDTO orders) {
            this.orders = orders;
        }
    }

    public class GetUserOrderListDTO{
        private int totalPrice, userId, status, id;
        private String address, customerName, createDate;

        public GetUserOrderListDTO(int totalPrice, int userId, int status, int id, String address, String customerName, String createDate) {
            this.totalPrice = totalPrice;
            this.userId = userId;
            this.status = status;
            this.id = id;
            this.address = address;
            this.customerName = customerName;
            this.createDate = createDate;
        }

        public int getTotalPrice() {
            return totalPrice;
        }

        public void setTotalPrice(int totalPrice) {
            this.totalPrice = totalPrice;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
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

}
