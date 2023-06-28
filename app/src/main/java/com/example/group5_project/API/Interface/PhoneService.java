package com.example.group5_project.API.Interface;

import com.example.group5_project.Entity.Phone;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface PhoneService {
    String PHONES = "phones";
    @GET(PHONES)
    Call<Phone[]> getAllPhones();

    @GET(PHONES + "/{id}")
    Call<Phone> getPhone(@Path("id") Object id);

    @GET(PHONES + "/{keyword}")
    Call<Phone> searchPhone(@Path("keyword") String keyword);

    @GET(PHONES)
    //PHONES?page=2&perPage=10
    Call<Phone[]> getAllPhones(@Query("page") int page, @Query("perPage") int perPage);

    @POST(PHONES)
    Call<Phone> createPhone(@Body Phone phone);

    @PUT(PHONES)
    Call<Phone> updatePhone(@Path("id") Object id, @Body Phone phone);

    @DELETE(PHONES + "/{id}")
    Call<Phone> deletePhone(@Path("id") Object id);
}
