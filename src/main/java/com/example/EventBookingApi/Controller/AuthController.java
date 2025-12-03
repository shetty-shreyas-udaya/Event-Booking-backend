package com.example.EventBookingApi.Controller;

import com.example.EventBookingApi.DTO.JwtResponse;
import com.example.EventBookingApi.DTO.LoginRequest;
import com.example.EventBookingApi.Model.User;
import com.example.EventBookingApi.Service.DataStore;
import com.example.EventBookingApi.Service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AuthController {


    @Autowired
    public JwtService jwtService;

    @Autowired
    public DataStore dataStore;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest loginRequest)
    {
        System.out.println("POST /login userName: " + loginRequest.getUserName());
        System.out.println("POST /login password: " + loginRequest.getPassword());
        User found = dataStore.getUserData().stream()
                .filter(u -> u.getUserName().equals(loginRequest.getUserName())
                && u.getPassword().equals(loginRequest.getPassword()))
                .findFirst().orElse(null);
        if(found == null) {
            return ResponseEntity.status(401).body("Invalid Credentials");
        }

        String token = jwtService.generateToken(found.getUserName(), found.getRole());
        System.out.println("Role:"+found.getRole());
        return ResponseEntity.ok(new JwtResponse(token,found.getRole(),found.getUserName()));
    }
}
