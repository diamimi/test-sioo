package com.controller;

import com.pojo.SendingVo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @Author: HeQi
 * @Date:Create in 9:53 2018/9/11
 */
@Controller
@RequestMapping("/about")
public class TestController {



    @RequestMapping("test")
    public String test(@ModelAttribute SendingVo sendingVo){
        System.out.println("手机号是:"+sendingVo.getMobile()+",UID:"+sendingVo.getUid());
        return "export/test";
    }
}
