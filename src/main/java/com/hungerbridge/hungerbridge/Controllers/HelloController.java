package com.hungerbridge.hungerbridge.Controllers;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/welcome")
@RestController
public class HelloController {

    @GetMapping("/greet")
    public String greet(){
        return "Welcome to the Hunger Bridge.com";
    }
}
