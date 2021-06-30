package com.ruoyi.cms.controller;

import com.ruoyi.common.core.web.controller.BaseController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



@RestController
@RequestMapping("/hardy")
public class FirstController extends BaseController {

    @GetMapping("/get")
    public String first(){
        return "hardy";
    }

}
