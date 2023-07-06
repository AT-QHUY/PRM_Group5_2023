package com.example.group5_project.API.Interface;

import com.example.group5_project.Entity.Category;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CategoryService {
    String CATEGORIES = "categories";
    @GET(CATEGORIES)
    Call<Category[]> getAllCategories();

    @GET(CATEGORIES + "/{id}")
    Call<Category> getCategory(@Path("id") Object id);

    @POST(CATEGORIES)
    Call<Category> createCategory(@Body Category category);

    @PUT(CATEGORIES)
    Call<Category> updateCategory(@Path("id") Object id, @Body Category category);

    @DELETE(CATEGORIES + "/{id}")
    Call<Category> deleteCategory(@Path("id") Object id);
}
