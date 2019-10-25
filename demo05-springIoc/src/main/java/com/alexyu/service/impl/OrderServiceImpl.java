package com.alexyu.service.impl;

import com.alexyu.service.OrderService;
import com.alexyu.spring.extannotation.ExtService;

/**
 * @author Alex Yu
 * @date 2019/8/1 1:11
 */
@ExtService
public class OrderServiceImpl implements OrderService {

    @Override
    public void addOrder() {
        System.out.println("addOrder");
    }
}
