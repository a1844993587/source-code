package com.alexyu.controller;

import com.alexyu.ext.annotation.ExtController;
import com.alexyu.ext.annotation.ExtRequestMapping;

/**
 * @author Alex Yu
 * @date 2019/8/1 23:17
 */
@ExtController
@ExtRequestMapping("/ext")
public class ExtIndexController {

    @ExtRequestMapping("/test")
    public String test() {
        System.out.println("手写springmvc框架");
        return "index1";
    }

}
