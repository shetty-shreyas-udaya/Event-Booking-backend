package com.example.EventBookingApi.Model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    private String role;
    private String userName;
    private String password;
}
