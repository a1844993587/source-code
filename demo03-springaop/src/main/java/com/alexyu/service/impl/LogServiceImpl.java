package com.alexyu.service.impl;

import com.alexyu.dao.LogDao;
import com.alexyu.service.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author Alex Yu
 * @date 2019/7/31 14:06
 */
@Service
public class LogServiceImpl implements LogService {

    @Autowired
    private LogDao logDao;

    @Override
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void addLog() {
        logDao.add("addLog" + System.currentTimeMillis());
    }

}
