package com.alexyu.service.impl;

import com.alexyu.service.OrderService;
import com.alexyu.service.UserService;
import com.alexyu.spring.extannotation.ExtResource;
import com.alexyu.spring.extannotation.ExtService;


//user 服务层
@ExtService
public class UserServiceImpl implements UserService {

	@ExtResource
	private OrderService orderServiceImpl;

	public void add() {
		orderServiceImpl.addOrder();
		System.out.println("使用反射机制初始化对象");
	}

}
