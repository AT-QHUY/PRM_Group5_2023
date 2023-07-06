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
    @GET(USERS)
    Call<User[]> getAllUsers();

    @GET(USERS + "/{id}")
    Call<User> getUser(@Path("id") Object id);

    @GET(USERS + "/{keyword}")
    Call<User> searchUser(@Path("keyword") String keyword);

    @POST(USERS)
    Call<User> createUser(@Body User user);

    @PUT(USERS)
    Call<User> updateUser(@Path("id") Object id, @Body User user);

    @DELETE(USERS + "/{id}")
    Call<User> deleteUser(@Path("id") Object id);
}
