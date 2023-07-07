package com.example.group5_project.API.Repository;

import com.example.group5_project.API.Client.APIClient;
import com.example.group5_project.API.Interface.PhoneService;

public class PhoneRepository {
    public static PhoneService getPhoneService(){
        return APIClient.getClient().create(PhoneService.class);
    }
}
