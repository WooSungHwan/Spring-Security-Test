package com.sunghwan.example.home;

import com.sunghwan.example.home.domain.Home;
import com.sunghwan.example.home.mapper.HomeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HomeController {

    @Autowired
    private HomeMapper homeMapper;

    @GetMapping("/")
    public String home(){
        return "Hello World";
    }

    @RequestMapping("/{name}")
    public Home home(@PathVariable String name){
        Home home = homeMapper.readHome(name);
        return home;
    }

    @GetMapping("/admin")
    public String admin(){
        return "This is admin page";
    }

    @GetMapping("/user")
    public String user(){
        return "This is user page";
    }
}