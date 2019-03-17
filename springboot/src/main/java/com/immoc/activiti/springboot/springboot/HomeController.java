package com.immoc.activiti.springboot.springboot;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @program: activiti6-sample
 * @description: Controller
 * @author: GilbertXiao
 * @create: 2019-03-11 22:11
 **/

@RestController
public class HomeController {

    @RequestMapping("/home")
    public String home(){
        return "Hello world!!!";
    }
}
