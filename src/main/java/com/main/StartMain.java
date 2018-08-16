package com.main;

import com.service.MobileAreaService21;
import com.service.SendHistory35Service;
import com.service.SendHistoryService;
import com.service.SendHistoryService114;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * @Author: HeQi
 * @Date:Create in 8:47 2018/7/18
 */
@Component
public class StartMain implements ApplicationRunner {

    private static final Logger LOGGER = LoggerFactory.getLogger(StartMain.class);


    @Autowired
    private SendHistoryService sendHistoryService;

    @Autowired
    private SendHistory35Service sendHistory35Service;

    @Autowired
    private MobileAreaService21 mobileAreaService21;


    @Autowired
    private SendHistoryService114 sendHistoryService114;


    @Override
    public void run(ApplicationArguments var1) throws Exception {
     //  exportBatch();
    }



}
