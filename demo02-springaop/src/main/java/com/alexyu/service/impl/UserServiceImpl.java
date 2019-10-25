package com.alexyu.service.impl;

import com.alexyu.dao.UserDao;
import com.alexyu.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Alex Yu
 * @date 2019/7/31 1:14
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;
//    @Autowired
//    private TransactionUtils transactionUtils;

    /*
    @Override
    public void add() {
        TransactionStatus transactionStatus = null;
        try {
            // 开启事务
            transactionStatus = this.transactionUtils.begin();
            this.userDao.add("test01", 20);
            System.out.println("##############");
//            int i = 1 / 0;
            this.userDao.add("test02", 21);
            // 提交事务
            if (transactionStatus != null) this.transactionUtils.commit(transactionStatus);
        } catch (Exception e) {
            e.printStackTrace();
            // 回滚事务
            if (transactionStatus != null) this.transactionUtils.rollback(transactionStatus);
        }
    }
    */

    @Override
    public void add() {
        this.userDao.add("test01", 20);
        System.out.println("##############");
            int i = 1 / 0;
        this.userDao.add("test02", 21);
    }
}
