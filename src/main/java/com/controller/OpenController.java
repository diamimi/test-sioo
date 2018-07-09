package com.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Author: HeQi
 * @Date:Create in 11:03 2017/12/14
 */
@RestController
public class OpenController {

    @RequestMapping("/open")
    public String open(){
        return "open ,account";
    }
}
