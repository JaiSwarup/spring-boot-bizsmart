package com.jai.bizsmart.Controller;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {
    @GetMapping("/")
    public String redirectTo(){
        return "redirect:/api/product";
    }
}
