package com.example.springsecuritydemo.controllers;

import com.example.springsecuritydemo.entities.User;
import com.example.springsecuritydemo.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequiredArgsConstructor
public class DemoController {
    private final UserService userService;

    @GetMapping("/")
    public String homePage(){
        return "home";
    }

    @GetMapping("/unsecured")
    public String unsecuredPage(){
        return "unsecured";
    }

    @GetMapping("/auth_page")
    public String auth_page(){
        return "auth_page";
    }

    @GetMapping("/admin")
    public String admin(){
        return "admin";
    }

    @GetMapping("/user_info")
    public String user_info(Principal principal){
        User user = userService.findByUsername(principal.getName()).orElseThrow(() -> new RuntimeException());
        return "Auth info: " + user.getUsername() + " : " + user.getEmail();
    }
    @PostMapping("/admin/{id}")
    public String updateUserEmail(@PathVariable("id") Long id, @RequestBody String email){
        boolean status = userService.updateUserEmail(email, id);
        return "Update status: " + status;
    }

    @DeleteMapping("/admin/{id}")
    public String deleteUserEmail(@PathVariable("id") Long id){
        boolean status = userService.deleteUser(id);
        return "Update status: " + status;
    }

}
