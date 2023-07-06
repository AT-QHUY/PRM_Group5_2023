package com.example.group5_project.API.Repository;

import com.example.group5_project.API.Client.APIClient;
import com.example.group5_project.API.Interface.CategoryService;
import com.example.group5_project.API.Interface.OrderDetailService;

public class CategoryRepository {
    public static CategoryService getCategoryService(){
        return APIClient.getClient().create(CategoryService.class);
    }
}
