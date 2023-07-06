package com.example.group5_project.API.Repository;

import com.example.group5_project.API.Client.APIClient;
import com.example.group5_project.API.Interface.UserService;

public class UserRepository {
    public static UserService getUserService(){
        return APIClient.getClient().create(UserService.class);
    }
}
